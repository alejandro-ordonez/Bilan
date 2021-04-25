/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.RegisterDto;
import org.bilan.co.data.TeachersRepository;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.enums.UserState;
import org.bilan.co.domain.entities.Teachers;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterService implements IRegisterService {
    private final TeachersRepository teachersRepository;
    
    RegisterService(TeachersRepository repository){
        this.teachersRepository = repository;
    }
    @Override
    public ResponseDto<UserState> userExists(RegisterDto registerDto) {
        log.error("Document: " + registerDto.getUserType());
        Teachers teacher = null;
        if(registerDto.getUserType().equals("teacher")){
            log.error(registerDto.getDocument());
            log.error(teachersRepository.toString());
            teacher = teachersRepository.findByDocument(registerDto.getDocument());
        }

        if(teacher == null){
            // validate from SIMAT
            return new ResponseDto<>("User does not exist", 404, UserState.UserNotFound);
        }

        if(teacher.getPassword() == null || teacher.getPassword() == ""){
            return new ResponseDto<>("User is not registered", 200, UserState.UserWithoutPassword);
        }

        return new ResponseDto<>("User already exist", 500, UserState.UserExists);
    }

}
