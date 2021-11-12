package org.bilan.co.application.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.RegisterUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.entities.StudentStats;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.domain.entities.builders.StudentsBuilder;
import org.bilan.co.domain.enums.UserState;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.client.SimatMatriculaClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.matricula.Matricula;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public ResponseDto<UserState> updateUser(AuthDto authDto) {
        switch (authDto.getUserType()) {
            case Teacher:
                return this.updateTeacher(authDto.getDocument(), authDto.getPassword());
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

    private ResponseDto<UserState> checkTeacher(AuthDto authDto) {
        Teachers teacher = teachersRepository.findByDocument(authDto.getDocument());

        if (teacher == null) {
            // validate from SIMAT
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Teacher does not exist")
                    .setCode(404)
                    .setResult(UserState.UserNotFound)
                    .createResponseDto();
        }

        if (teacher.getPassword() == null || teacher.getPassword().equals("")) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Teacher is not registered")
                    .setCode(200)
                    .setResult(UserState.UserWithoutPassword)
                    .createResponseDto();
        }

        return new ResponseDtoBuilder<UserState>()
                .setDescription("Teacher already exist")
                .setCode(500)
                .setResult(UserState.UserExists)
                .createResponseDto();
    }

    private ResponseDto<UserState> checkStudent(AuthDto authDto) {
        Students student = Optional.ofNullable(studentsRepository.findByDocument(authDto.getDocument()))
                .orElseGet(() -> createNewStudent(authDto));

        if (Objects.isNull(student)) {
            return userDoesNotExists();
        }

        if (Strings.isBlank(student.getPassword())) {
            return userNoPassword();
        }

        return userAlreadyExists();
    }

    private Students createNewStudent(AuthDto authDto) {
        return this.simatEstudianteClient.getStudent(authDto.getDocument())
                .map(student -> studentWithEnrollment(student, authDto.getDocument()))
                .map(tuple -> newStudent(authDto, tuple.getValue1(), tuple.getValue2()))
                .map(newStudent -> studentsRepository.save(newStudent))
                .orElse(null);
    }

    private Tuple<Estudiante, Matricula> studentWithEnrollment(Estudiante estudiante, String document) {
        return this.simatMatriculaClient.getMatricula(document)
                .map(enrollment -> new Tuple<>(estudiante, enrollment))
                .orElseGet(() -> new Tuple<>(estudiante, new Matricula()));
    }

    private Students newStudent(AuthDto authDto, Estudiante estudiante, Matricula matricula) {
        return new StudentsBuilder()
                .setDocument(authDto.getDocument())
                .setDocumentType(authDto.getDocumentType())
                .setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre())
                .setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido())
                .setGrade(matricula.getCodGradoEducativo())
                .createStudents();
    }

    private ResponseDto<UserState> userDoesNotExists() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("Student does not exist")
                .setCode(404)
                .setResult(UserState.UserNotFound)
                .createResponseDto();
    }

    private ResponseDto<UserState> userNoPassword() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("Student is not registered")
                .setCode(200)
                .setResult(UserState.UserWithoutPassword)
                .createResponseDto();
    }

    private ResponseDto<UserState> userAlreadyExists() {
        return new ResponseDtoBuilder<UserState>()
                .setDescription("Student already exist")
                .setCode(500)
                .setResult(UserState.UserExists)
                .createResponseDto();
    }

    private ResponseDto<UserState> updateStudent(AuthDto authDto) {
        Students student = studentsRepository.findByDocument(authDto.getDocument());

        if (student == null || student.getPassword() != null) {
            return userAlreadyExists();
        }

        String encryptedPassword = passwordEncoder.encode(authDto.getPassword());
        student.setPassword(encryptedPassword);
        student.setGrade(authDto.getGrade());

        try {
            studentsRepository.save(student);
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Student updated successfully")
                    .setCode(200)
                    .setResult(UserState.UserUpdated)
                    .createResponseDto();

        } catch (Exception e) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Error saving student")
                    .setCode(500)
                    .setResult(UserState.UserRegistered)
                    .createResponseDto();
        }
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
        Teachers teacher = teachersRepository.findByDocument(regUserDto.getDocument());
        if (Objects.nonNull(teacher)) {
            return new ResponseDto<>("Teacher already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        teacher = new Teachers();
        teacher.setName(regUserDto.getName());
        teacher.setLastName(regUserDto.getLastname());
        teacher.setEmail(regUserDto.getEmail());
        teacher.setCreatedAt(new Date());
        teacher.setPassword(regUserDto.getPassword());
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
        Students student = studentsRepository.findByDocument(regUserDto.getDocument());
        if (Objects.nonNull(student)) {
            return new ResponseDto<>("Student already exists",
                    HttpStatus.BAD_REQUEST.value(), UserState.UserExists);
        }

        student = new Students();
        student.setName(regUserDto.getName());
        student.setGrade(regUserDto.getGrade());
        student.setDocument(regUserDto.getDocument());
        student.setLastName(regUserDto.getLastname());
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

    private ResponseDto<UserState> updateTeacher(String document, String password) {
        Teachers teacher = teachersRepository.findByDocument((document));

        if (teacher == null || teacher.getPassword() != null) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Teacher already exist")
                    .setCode(500)
                    .setResult(UserState.UserExists)
                    .createResponseDto();
        }

        String encryptedPassword = passwordEncoder.encode(password);
        teacher.setPassword(encryptedPassword);

        try {
            teachersRepository.save(teacher);
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Teacher updated successfully")
                    .setCode(200).setResult(UserState.UserUpdated)
                    .createResponseDto();

        } catch (Exception e) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Error saving teacher")
                    .setCode(500)
                    .setResult(UserState.UserRegistered)
                    .createResponseDto();
        }
    }
}
