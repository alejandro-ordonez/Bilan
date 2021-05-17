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
import org.bilan.co.domain.dtos.RegisterUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.enums.UserState;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.client.SimatMatriculaClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.matricula.Matricula;
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
    private SimatMatriculaClient simatMatriculaClient;
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
                return new ResponseDto<>("UserType does not exist", 500, UserState.UnknownUserType);
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
                return new ResponseDto<>("UserType does not exist", 500, UserState.UnknownUserType);
        }
    }

    private ResponseDto<UserState> checkTeacher(AuthDto authDto) {
        Teachers teacher = teachersRepository.findByDocument(authDto.getDocument());

        if (teacher == null) {
            // validate from SIMAT
            return new ResponseDto<>("Teacher does not exist", 404, UserState.UserNotFound);
        }

        if (teacher.getPassword() == null || teacher.getPassword().equals("")) {
            return new ResponseDto<>("Teacher is not registered", 200, UserState.UserWithoutPassword);
        }

        return new ResponseDto<>("Teacher already exist", 500, UserState.UserExists);
    }

    private ResponseDto<UserState> checkStudent(AuthDto authDto) {
        Students student = studentsRepository.findByDocument(authDto.getDocument());

        if (student == null) {
            Optional<Estudiante> optionalEstudiante = this.simatEstudianteClient.getStudent(authDto.getDocument());
            if (!optionalEstudiante.isPresent()) {
                return new ResponseDto<>("Student does not exist", 404, UserState.UserNotFound);
            }
            Estudiante estudiante = optionalEstudiante.get();
            Students newStudent = new Students();
            newStudent.setDocument(authDto.getDocument());
            newStudent.setDocumentType(authDto.getDocumentType());
            newStudent.setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre());
            newStudent.setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido());
            newStudent.setCreatedAt(new Date());
            newStudent.setModifiedAt(new Date());

            log.debug("POR ACA");

            //TODO: create Students Classrooms
            Optional<Matricula> optionalMatricula = this.simatMatriculaClient.getMatricula(authDto.getDocument());
            if (optionalMatricula.isPresent()) {
                log.debug(optionalMatricula.get().getNomGrupo() + optionalMatricula.get().getCodGradoEducativo());
            }
            student = studentsRepository.save(newStudent);
        }

        if (student.getPassword() == null || student.getPassword().equals("")) {
            return new ResponseDto<>("Student is not registered", 200, UserState.UserWithoutPassword);
        }

        return new ResponseDto<>("Student already exist", 500, UserState.UserExists);
    }

    private ResponseDto<UserState> updateStudent(String document, String password) {
        Students student = studentsRepository.findByDocument((document));
        if (student == null || student.getPassword() != null) {
            return new ResponseDto<>("Student already exist", 500, UserState.UserExists);
        }
        String encryptedPassword = cryptPasswordEncoder.encode(password);
        student.setPassword(encryptedPassword);

        try {
            studentsRepository.save(student);
            return new ResponseDto<>("Student updated successfully", 200, UserState.UserUpdated);
        } catch (Exception e) {
            return new ResponseDto<>("Error saving student", 500, UserState.UserRegistered);
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

        String encryptedPassword = cryptPasswordEncoder.encode(regUserDto.getPassword());
        student.setPassword(encryptedPassword);

        //TODO: add classRoom

        try {
            studentsRepository.save(student);
            return new ResponseDto<>("Student registered successfully", 200, UserState.UserRegistered);
        } catch (Exception e) {
            return new ResponseDto<>("Student already exists", 500, UserState.UserExists);
        }
    }

    private ResponseDto<UserState> updateTeacher(String document, String password) {
        Teachers teacher = teachersRepository.findByDocument((document));
        if (teacher == null || teacher.getPassword() != null) {
            return new ResponseDto<>("Teacher already exists", 500, UserState.UserExists);
        }
        String encryptedPassword = cryptPasswordEncoder.encode(password);
        teacher.setPassword(encryptedPassword);

        try {
            teachersRepository.save(teacher);
            return new ResponseDto<>("Teacher updated successfully", 200, UserState.UserUpdated);
        } catch (Exception e) {
            return new ResponseDto<>("Error saving teacher", 500, UserState.UserRegistered);
        }
    }
}
