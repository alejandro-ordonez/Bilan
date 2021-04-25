/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.data.StudentsRepository;
import org.bilan.co.domain.dtos.RegisterDto;
import org.bilan.co.data.TeachersRepository;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.enums.UserState;
import org.bilan.co.domain.dtos.enums.UserTypes;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RegisterService implements IRegisterService {
    private final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository;
    private final SimatEstudianteClient simatEstudianteClient;

    RegisterService(
            TeachersRepository teachersRepository,
            StudentsRepository studentsRepository,
            SimatEstudianteClient simatEstudianteClient
    ){
        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
        this.simatEstudianteClient = simatEstudianteClient;
    }
    @Override
    public ResponseDto<UserState> userExists(RegisterDto registerDto) {
        log.error("Receiving registerDTO with {}", registerDto.getDocument());
        ResponseDto<UserState> responseDto = null;
        if(registerDto.getUserType().equals(UserTypes.Teacher.toString())){
            log.error("Checking {}", registerDto.getDocumentType());
            responseDto = this.checkTeacher(registerDto);
        }else{
            log.error("Checking {}", registerDto.getDocumentType());
            responseDto = this.checkStudent(registerDto);
        }

        return responseDto;
    }

    @Override
    public ResponseDto<UserState> updateUser(RegisterDto registerDto) {

        return null;
    }

    private  ResponseDto<UserState> checkTeacher(RegisterDto registerDto){
        Teachers teacher = teachersRepository.findByDocument(registerDto.getDocument());;

        if(teacher == null){
            // validate from SIMAT
            return new ResponseDto<>("Teacher does not exist", 404, UserState.UserNotFound);
        }

        if(teacher.getPassword() == null || teacher.getPassword() == ""){
            return new ResponseDto<>("Teacher is not registered", 200, UserState.UserWithoutPassword);
        }

        return new ResponseDto<>("Teacher already exist", 500, UserState.UserExists);
    }

    private ResponseDto<UserState> checkStudent(RegisterDto registerDto){
        Students student = studentsRepository.findByDocument(registerDto.getDocument());

        if(student == null){
            // validate from SIMAT
            Optional<Estudiante> optionalEstudiante = this.simatEstudianteClient.getStudent(registerDto.getDocument());
            if(!optionalEstudiante.isPresent()){
                return new ResponseDto<>("Student does not exist", 404, UserState.UserNotFound);
            }
            Estudiante estudiante = optionalEstudiante.get();
            log.error("Estudente Simat {}", estudiante.getIdentificacion());
            //add new student with estudianteSimat data
            Students newStudent = new Students();
            newStudent.setDocument(registerDto.getDocument());
            newStudent.setDocumentType(registerDto.getDocumentType());
            newStudent.setName(estudiante.getPrimerNombre() + " " + estudiante.getSegundoNombre());
            newStudent.setLastName(estudiante.getPrimerApellido() + " " + estudiante.getSegundoApellido());
            //TODO: create Students Classrooms
            student = studentsRepository.save(newStudent);
        }

        if(student.getPassword() == null || student.getPassword() == ""){
            return new ResponseDto<>("Student is not registered", 200, UserState.UserWithoutPassword);
        }

        return new ResponseDto<>("Student already exist", 500, UserState.UserExists);
    }
}
