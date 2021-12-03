package org.bilan.co.application.user;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.dtos.user.RegisterUserDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.entities.builders.StudentsBuilder;
import org.bilan.co.domain.enums.UserState;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.client.SimatMatriculaClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.matricula.Matricula;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class RegisterService implements IRegisterService {

    private final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository;
    private final SimatEstudianteClient simatEstudianteClient;
    private final SimatMatriculaClient simatMatriculaClient;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(TeachersRepository teachersRepository, StudentsRepository studentsRepository,
                           SimatEstudianteClient simatEstudianteClient, SimatMatriculaClient simatMatriculaClient,
                           PasswordEncoder passwordEncoder) {

        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
        this.simatEstudianteClient = simatEstudianteClient;
        this.simatMatriculaClient = simatMatriculaClient;
        this.passwordEncoder = passwordEncoder;

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
        } else if (!student.getConfirmed()) {
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
        return new StudentsBuilder()
                .setDocument(authDto.getDocument())
                .setDocumentType(authDto.getDocumentType())
                .setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre())
                .setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido())
                .createStudents();
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
            return userNotRegistered();
        }
    }

    @NotNull
    private Students updateFieldsStudent(AuthDto authDto, Students student) {
        String encryptedPassword = passwordEncoder.encode(authDto.getPassword());
        student.setPassword(encryptedPassword);
        student.setGrade(authDto.getGrade());
        Courses course = new Courses();
        course.setId(authDto.getCourseId());
        student.setCourses(course);
        student.setConfirmed(true);
        student.setModifiedAt(new Date());
        student.setEmail(authDto.getEmail());
        Colleges colleges = new Colleges();
        colleges.setId(authDto.getCollegeId());
        student.setColleges(colleges);
        return student;
    }

    public ResponseDto<UserState> createUser(RegisterUserDto regUserDto) {
        switch (regUserDto.getUserType()) {
            case Teacher:
                return this.createTeacher(regUserDto);
            case Student:
                return this.createStudent(regUserDto);
            default:
                return new ResponseDtoBuilder<UserState>()
                        .setDescription("UserType does not exist")
                        .setCode(HttpStatus.BAD_REQUEST.value())
                        .setResult(UserState.UnknownUserType)
                        .createResponseDto();
        }
    }

    private ResponseDto<UserState> createTeacher(RegisterUserDto regUserDto) {
        Teachers teacher = teachersRepository.findById(regUserDto.getDocument()).orElse(null);
        if (Objects.nonNull(teacher)) {
            return new ResponseDto<>("Teacher already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        teacher = new Teachers();
        teacher.setDocument(regUserDto.getDocument());
        teacher.setName(regUserDto.getName());
        teacher.setLastName(regUserDto.getLastName());
        teacher.setEmail(regUserDto.getEmail());
        teacher.setCreatedAt(new Date());
        teacher.setPassword(this.passwordEncoder.encode(regUserDto.getPassword()));
        teacher.setDocumentType(regUserDto.getDocumentType());

        try {
            teachersRepository.save(teacher);
            return new ResponseDto<>("Teacher registered successfully", HttpStatus.OK.value(),
                    UserState.UserRegistered);
        } catch (Exception e) {
            log.error("Something was wrong saving the user info", e);
            return new ResponseDto<>("Something was wrong", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    UserState.UserNotRegistered);
        }
    }

    private ResponseDto<UserState> createStudent(RegisterUserDto regUserDto) {
        Students student = studentsRepository.findById(regUserDto.getDocument()).orElse(null);
        if (Objects.nonNull(student)) {
            return new ResponseDto<>("Student already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        student = new Students();
        student.setName(regUserDto.getName());
        student.setGrade(regUserDto.getGrade());
        student.setDocument(regUserDto.getDocument());
        student.setLastName(regUserDto.getLastName());
        student.setEmail(regUserDto.getEmail());
        student.setDocumentType(regUserDto.getDocumentType());
        student.setCreatedAt(new Date());
        student.setModifiedAt(new Date());
        student.setPassword(passwordEncoder.encode(regUserDto.getPassword()));

        try {
            StudentStats studentStats = new StudentStats();
            student.setStudentStats(studentStats);
            studentStats.setIdStudent(student);
            studentsRepository.save(student);
            return new ResponseDto<>("Student registered successfully", HttpStatus.OK.value(),
                    UserState.UserRegistered);
        } catch (Exception e) {
            log.error("Something was wrong saving the user info", e);
            return new ResponseDto<>("Something was wrong", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    UserState.UserNotRegistered);
        }
    }
}
