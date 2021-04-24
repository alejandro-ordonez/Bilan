package org.bilan.co.application;

import org.bilan.co.data.StudentsRepository;
import org.bilan.co.data.TeachersRepository;
import org.bilan.co.domain.dtos.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.enums.UserType;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UserService {

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeachersRepository teachersRepository;

    public AuthenticatedUserDto getUserNameTokenById(Map<String, Object> dataToken){
        String document = (String)dataToken.get(JwtTokenUtil.DOCUMENT);
        String documentType = (String) dataToken.get(JwtTokenUtil.DOCUMENT_TYPE);
        UserType userType =  UserType.valueOf((String) dataToken.get(JwtTokenUtil.USER_TYPE));

        switch (userType){

            case Student:
                Students students = studentsRepository.findByNumber(document);
                if(students == null)
                    return new AuthenticatedUserDto("", "", "");
                return new AuthenticatedUserDto(students.getDocument(), userType.name(), students.getDocumentType());

            case Teacher:
                Teachers teachers = teachersRepository.findTeacherByDocument(document);
                if(teachers == null)
                    return new AuthenticatedUserDto("", "", "");
                return new AuthenticatedUserDto(teachers.getDocument(), userType.name(), teachers.getDocumentType());
            default:
                return new AuthenticatedUserDto("", "", "");
        }
    }
}
