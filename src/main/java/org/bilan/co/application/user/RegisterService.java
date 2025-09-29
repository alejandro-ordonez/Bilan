package org.bilan.co.application.user;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.dtos.user.RegisterUserDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.entities.builders.StudentsBuilder;
import org.bilan.co.domain.enums.UserState;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.Constants;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.client.SimatMatriculaClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.matricula.Matricula;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class RegisterService implements IRegisterService {

    private final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository;
    private final SimatEstudianteClient simatEstudianteClient;
    private final SimatMatriculaClient simatMatriculaClient;
    private final UserInfoRepository userInfoRepository;
    private final SecEduRepository secEduRepository;
    private final MinUserRepository minUserRepository;
    private final CollegesRepository collegesRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(TeachersRepository teachersRepository, StudentsRepository studentsRepository,
                           SimatEstudianteClient simatEstudianteClient, SimatMatriculaClient simatMatriculaClient,
                           PasswordEncoder passwordEncoder,
                           UserInfoRepository userInfoRepository,
                           MinUserRepository minUserRepository,
                           SecEduRepository secEduRepository,
                           CollegesRepository collegesRepository) {

        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
        this.simatEstudianteClient = simatEstudianteClient;
        this.simatMatriculaClient = simatMatriculaClient;
        this.passwordEncoder = passwordEncoder;
        this.userInfoRepository = userInfoRepository;
        this.minUserRepository = minUserRepository;
        this.collegesRepository = collegesRepository;
        this.secEduRepository = secEduRepository;

    }

    @Override
    public ResponseDto<UserState> userExists(AuthDto authDto) {
        switch (authDto.getUserType()) {
            case Teacher:
                return this.checkTeacher(authDto);
            case Student:
                return this.checkStudent(authDto);
            default:
                return new ResponseDtoBuilder<UserState>()
                        .setDescription("UserType does not exist")
                        .setCode(500)
                        .setResult(UserState.UnknownUserType)
                        .createResponseDto();
        }
    }

    private ResponseDto<UserState> checkTeacher(AuthDto authDto) {
        return teachersRepository.findById(authDto.getDocument())
                .map(teacher -> !teacher.getConfirmed() ? userRequireUpdate() : userAlreadyExists())
                .orElse(userNotFound());
    }

    private ResponseDto<UserState> checkStudent(AuthDto authDto) {
        Students student = studentsRepository.findById(authDto.getDocument())
                .orElseGet(() -> createNewStudent(authDto));

        if (Objects.isNull(student)) {
            return userNotFound();
        } else if (student.getDocumentType() != authDto.getDocumentType())
            return userNotFound();

        if (!student.getConfirmed()) {
            return userRequireUpdate();
        }

        return userAlreadyExists();
    }

    @Override
    public ResponseDto<UserState> updateUser(AuthDto authDto) {
        switch (authDto.getUserType()) {
            case Teacher:
                return this.updateTeacher(authDto);
            case Student:
                return this.updateStudent(authDto);
            default:
                return new ResponseDtoBuilder<UserState>()
                        .setDescription("UserType does not exist")
                        .setCode(500)
                        .setResult(UserState.UnknownUserType)
                        .createResponseDto();
        }
    }

    private Students createNewStudent(AuthDto authDto) {
        return this.simatEstudianteClient.getStudent(authDto.getDocument())
                .map(student -> studentWithEnrollment(student, authDto.getDocument()))
                .map(tuple -> newStudent(authDto, tuple.getValue1()))
                .map(studentsRepository::save)
                .orElse(null);
    }

    private Tuple<Estudiante, Matricula> studentWithEnrollment(Estudiante estudiante, String document) {
        return this.simatMatriculaClient.getMatricula(document)
                .map(enrollment -> new Tuple<>(estudiante, enrollment))
                .orElseGet(() -> new Tuple<>(estudiante, new Matricula()));
    }

    private Students newStudent(AuthDto authDto, Estudiante estudiante) {
        Students s = new StudentsBuilder()
                .setDocument(authDto.getDocument())
                .setDocumentType(authDto.getDocumentType())
                .setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre())
                .setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido())
                .createStudents();

        s.setConfirmed(false);
        return s;
    }

    private ResponseDto<UserState> userNotFound() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("User not found")
                .setCode(404)
                .setResult(UserState.UserNotFound)
                .createResponseDto();
    }

    private ResponseDto<UserState> userRequireUpdate() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("User requires to update its info")
                .setCode(417)
                .setResult(UserState.UserRequireUpdate)
                .createResponseDto();
    }

    private ResponseDto<UserState> userAlreadyExists() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("User already exist")
                .setCode(500)
                .setResult(UserState.UserExists)
                .createResponseDto();
    }

    private ResponseDto<UserState> userUpdated() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("User updated successfully")
                .setCode(200)
                .setResult(UserState.UserUpdated)
                .createResponseDto();
    }

    private ResponseDto<UserState> userNotRegistered() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("Error saving teacher")
                .setCode(500)
                .setResult(UserState.UserRegistered)
                .createResponseDto();
    }

    private ResponseDto<UserState> updateTeacher(AuthDto authDto) {
        return teachersRepository.findById(authDto.getDocument())
                .filter(teachers -> !teachers.getConfirmed())
                .map(teachers -> updateFieldsTeacher(authDto, teachers))
                .map(this::saveTeacher)
                .orElse(userNotFound());
    }

    private ResponseDto<UserState> saveTeacher(Teachers teacher) {
        try {
            teachersRepository.save(teacher);
            return userUpdated();
        } catch (Exception e) {
            return userNotRegistered();
        }
    }

    @NotNull
    private Teachers updateFieldsTeacher(AuthDto authDto, Teachers teachers) {
        teachers.setPassword(passwordEncoder.encode(authDto.getPassword()));
        teachers.setEmail(authDto.getEmail());
        teachers.setModifiedAt(new Date());
        teachers.setConfirmed(true);
        return teachers;
    }

    private ResponseDto<UserState> updateStudent(AuthDto authDto) {
        return studentsRepository.findById(authDto.getDocument())
                .map(student -> updateFieldsStudent(authDto, student))
                .map(this::saveStudent)
                .orElse(userNotFound());
    }

    private ResponseDto<UserState> saveStudent(Students student) {
        try {
            studentsRepository.save(student);
            return userUpdated();
        } catch (Exception e) {
            log.error("Error: ",e);
            return userNotRegistered();
        }
    }

    @NotNull
    private Students updateFieldsStudent(AuthDto authDto, Students student) {
        String encryptedPassword = passwordEncoder.encode(authDto.getPassword());
        student.setPassword(encryptedPassword);
        student.setConfirmed(true);
        student.setEmail(authDto.getEmail());
        return student;
    }

    public ResponseDto<UserState> createUser(RegisterUserDto regUserDto) {
        try{
            return switch (regUserDto.getUserType()) {
                case DirectiveTeacher, Teacher -> this.createTeacher(regUserDto);
                case Student -> this.createStudent(regUserDto);
                case MinUser -> this.createMinUser(regUserDto);
                case SecEdu -> this.createSecEdu(regUserDto);
                default -> new ResponseDtoBuilder<UserState>()
                        .setDescription("UserType does not exist")
                        .setCode(HttpStatus.BAD_REQUEST.value())
                        .setResult(UserState.UnknownUserType)
                        .createResponseDto();
            };
        }
        catch (Exception e){
            log.error("Failed to save the user: {}", regUserDto.getDocument(), e);
            return new ResponseDto<>("Something was wrong", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    UserState.UserNotRegistered);
        }
    }

    private<T extends UserInfo> T createBaseUser(Class<T>  userClass, RegisterUserDto regUserDto){

        T user = null;

        try {
            user = userClass.getDeclaredConstructor().newInstance();
            user.setDocument(regUserDto.getDocument());
            user.setName(regUserDto.getName());
            user.setLastName(regUserDto.getLastName());
            user.setEmail(regUserDto.getEmail());
            user.setPassword(this.passwordEncoder.encode(regUserDto.getPassword()));
            user.setDocumentType(regUserDto.getDocumentType());
            user.setConfirmed(false);
            user.setIsEnabled(true);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error trying to create an user", e);
        }

        return user;
    }

    private ResponseDto<UserState> createMinUser(RegisterUserDto registerUserDto){
        MinUser minUser = minUserRepository.findById(registerUserDto.getDocument()).orElse(null);
        if(Objects.nonNull(minUser)){
            return new ResponseDto<>("User already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        minUser = createBaseUser(MinUser.class, registerUserDto);
        Roles roles = new Roles();
        roles.setId(6);
        minUser.setRole(roles);

        minUser.setPositionName(Constants.MIN_USER);

        minUserRepository.save(minUser);

        return new ResponseDto<>("MinUser registered successfully", HttpStatus.OK.value(),
                UserState.UserRegistered);
    }

    private ResponseDto<UserState> createSecEdu(RegisterUserDto registerUserDto) {
        SecEduUser userInfo = createBaseUser(SecEduUser.class, registerUserDto);

        Roles roles = new Roles();
        roles.setId(4);

        userInfo.setRole(roles);
        userInfo.setState(registerUserDto.getSelectedState());

        secEduRepository.save(userInfo);
        return new ResponseDto<>("Sec Edu registered successfully", HttpStatus.OK.value(),
                UserState.UserRegistered);
    }

    private ResponseDto<UserState> createTeacher(RegisterUserDto regUserDto) {
        Teachers teacher = teachersRepository.findById(regUserDto.getDocument()).orElse(null);

        if (Objects.nonNull(teacher)) {
            return new ResponseDto<>("Teacher already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        teacher = createBaseUser(Teachers.class, regUserDto);

        Optional<Colleges> college = collegesRepository.findByCodDaneSede(regUserDto.getCodDane().trim());

        if (college.isEmpty()) {
            return new ResponseDto<>("College not found",
                    HttpStatus.BAD_REQUEST.value(), UserState.CollegeNotFound);
        }

        Roles role = new Roles();

        teacher.setCollege(college.get());

        if(regUserDto.getUserType().equals(UserType.DirectiveTeacher)){
            teacher.setPositionName(Constants.DIREC_TEACHER);
            role.setId(3);
            teacher.setRole(role);
            userInfoRepository.save(teacher);
        }

        else if(regUserDto.getUserType().equals(UserType.Teacher)){
            teacher.setPositionName(Constants.TEACHER);
            role.setId(2);
            teacher.setRole(role);
            teachersRepository.save(teacher);
        }

        return new ResponseDto<>("Teacher registered successfully", HttpStatus.OK.value(),
                UserState.UserRegistered);

    }

    private ResponseDto<UserState> createStudent(RegisterUserDto regUserDto) {
        Students student = studentsRepository.findById(regUserDto.getDocument()).orElse(null);
        if (Objects.nonNull(student)) {
            return new ResponseDto<>("Student already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        student = createBaseUser(Students.class, regUserDto);
        StudentStats studentStats = new StudentStats();
        student.setStudentStats(studentStats);
        studentStats.setIdStudent(student);

        Roles role = new Roles();
        role.setId(1);
        student.setRole(role);

        Courses course = new Courses();
        course.setId(regUserDto.getCourseId());

        student.setCourses(course);
        student.setGrade(regUserDto.getGrade());

        student.setPositionName(Constants.STUDENT);

        Colleges college = new Colleges();
        college.setId(regUserDto.getCollegeId());

        student.setColleges(college);
        studentsRepository.save(student);
        return new ResponseDto<>("Student registered successfully", HttpStatus.OK.value(),
                UserState.UserRegistered);
    }
}
