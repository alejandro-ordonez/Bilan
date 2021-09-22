/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.RegisterUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.entities.StudentStats;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.domain.entities.builders.StudentsBuilder;
import org.bilan.co.domain.enums.UserState;
import org.bilan.co.infraestructure.persistance.StatsRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.client.SimatMatriculaClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.matricula.Matricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class RegisterService implements IRegisterService {

    private TeachersRepository teachersRepository;
    private StudentsRepository studentsRepository;
    private SimatEstudianteClient simatEstudianteClient;
    private SimatMatriculaClient simatMatriculaClient;
    private PasswordEncoder passwordEncoder;

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
                return this.updateStudent(authDto.getDocument(), authDto.getPassword());

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
        Students student = studentsRepository.findByDocument(authDto.getDocument());

        if (student == null) {
            Optional<Estudiante> optionalEstudiante = this.simatEstudianteClient.getStudent(authDto.getDocument());

            if (!optionalEstudiante.isPresent()) {

                return new ResponseDtoBuilder<UserState>()
                        .setDescription("Student does not exist")
                        .setCode(404)
                        .setResult(UserState.UserNotFound).createResponseDto();
            }
            Estudiante estudiante = optionalEstudiante.get();
            log.error("Estudente Simat {}", estudiante.getIdentificacion());

            //add new student with estudianteSimat data
            Students newStudent = new StudentsBuilder()
                    .setDocument(authDto.getDocument())
                    .setDocumentType(authDto.getDocumentType())
                    .setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre())
                    .setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido())
                    .createStudents();

            //TODO: create Students Classrooms
            Optional<Matricula> optionalMatricula = this.simatMatriculaClient.getMatricula(authDto.getDocument());
            optionalMatricula.ifPresent(matricula -> log.debug(matricula.getNomGrupo() + matricula.getCodGradoEducativo()));
            student = studentsRepository.save(newStudent);
        }

        if (student.getPassword() == null || student.getPassword().equals("")) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Student is not registered")
                    .setCode(200)
                    .setResult(UserState.UserWithoutPassword)
                    .createResponseDto();
        }

        return new ResponseDtoBuilder<UserState>()
                .setDescription("Student already exist")
                .setCode(500)
                .setResult(UserState.UserExists)
                .createResponseDto();
    }

    private ResponseDto<UserState> updateStudent(String document, String password) {
        Students student = studentsRepository.findByDocument((document));

        if (student == null || student.getPassword() != null) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Student already exist")
                    .setCode(500)
                    .setResult(UserState.UserExists).createResponseDto();
        }

        String encryptedPassword = passwordEncoder.encode(password);
        student.setPassword(encryptedPassword);

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
        Students student = new Students();
        student.setName(regUserDto.getName());
        student.setDocument(regUserDto.getDocument());
        student.setLastName(regUserDto.getLastname());
        student.setEmail(regUserDto.getEmail());
        student.setDocumentType(regUserDto.getDocumentType());
        student.setCreatedAt(new Date());
        student.setModifiedAt(new Date());

        String encryptedPassword = passwordEncoder.encode(regUserDto.getPassword());
        student.setPassword(encryptedPassword);

        //TODO: add classRoom

        try {
            StudentStats studentStats = StudentStats.getDefault();
            student.setStudentStats(studentStats);
            studentStats.setIdStudent(student);
            studentsRepository.save(student);
            return new ResponseDto<>("Student registered successfully", 200, UserState.UserRegistered);
        } catch (Exception e) {
            return new ResponseDto<>("Student already exists", 500, UserState.UserExists);
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
