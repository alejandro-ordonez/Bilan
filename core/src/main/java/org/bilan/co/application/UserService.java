package org.bilan.co.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.data.StudentsRepository;
import org.bilan.co.data.TeachersRepository;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.enums.DocumentType;
import org.bilan.co.domain.dtos.enums.UserType;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Service
public class UserService implements UserDetailsService{

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeachersRepository teachersRepository;

    public AuthenticatedUserDto getUserNameTokenById(Map<String, Object> dataToken){
        String document = (String)dataToken.get(JwtTokenUtil.DOCUMENT);
        UserType userType =  UserType.valueOf((String) dataToken.get(JwtTokenUtil.USER_TYPE));

        switch (userType){

            case Student:
                Students students = studentsRepository.findByDocument(document);
                if(students == null)
                    return new AuthenticatedUserDto("", "", DocumentType.Unknown);
                return new AuthenticatedUserDto(students.getDocument(), userType.name(), students.getDocumentType());

            case Teacher:
                Teachers teachers = teachersRepository.findByDocument(document);
                if(teachers == null)
                    return new AuthenticatedUserDto("", "", DocumentType.Unknown);
                return new AuthenticatedUserDto(teachers.getDocument(), userType.name(), teachers.getDocumentType());
            default:
                return new AuthenticatedUserDto("", "", DocumentType.Unknown);
        }
    }

    private String getCredentials(String data) throws IOException {
        log.info("Getting credentials");
        AuthDto authDto = new ObjectMapper().readValue(data, AuthDto.class);
        switch (authDto.getUserType()) {
            case Teacher:
                Teachers teachers = teachersRepository.findByDocument(authDto.getDocument());
                return teachers.getPassword();
            case Student:
                Students students = studentsRepository.findByDocument(authDto.getDocument());
                return students.getPassword();
            default:
                return "";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info(username);
            String password = getCredentials(username);
            return new User(username, password, new ArrayList<>());
        } catch (IOException e) {
            log.error("Failed to deserialize", e);
            return new User("", "", new ArrayList<>());
        }
    }
}
