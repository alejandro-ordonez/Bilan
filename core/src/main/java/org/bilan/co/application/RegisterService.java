/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.data.StudentsRepository;
import org.bilan.co.data.TeachersRepository;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.enums.UserState;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class RegisterService implements IRegisterService {
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private SimatEstudianteClient simatEstudianteClient;
    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

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
            // validate from SIMAT
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
            Students newStudent = new Students();
            newStudent.setDocument(authDto.getDocument());
            newStudent.setDocumentType(authDto.getDocumentType());
            newStudent.setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre());
            newStudent.setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido());
            newStudent.setCreatedAt(new Date());
            newStudent.setModifiedAt(new Date());

            //TODO: create Students Classrooms
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

        String encryptedPassword = cryptPasswordEncoder.encode(password);
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

    private ResponseDto<UserState> updateTeacher(String document, String password) {
        Teachers teacher = teachersRepository.findByDocument((document));

        if (teacher == null || teacher.getPassword() != null) {
            return new ResponseDtoBuilder<UserState>()
                    .setDescription("Teacher already exist")
                    .setCode(500)
                    .setResult(UserState.UserExists)
                    .createResponseDto();
        }

        String encryptedPassword = cryptPasswordEncoder.encode(password);
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
