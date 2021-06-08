package org.bilan.co.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.entities.StudentChallenges;
import org.bilan.co.infraestructure.persistance.StatsRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.domain.dtos.*;
import org.bilan.co.domain.entities.StudentStats;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService{

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AuthenticatedUserDto getUserNameTokenById(AuthenticatedUserDto dataToken){

        Object user = getUser(dataToken);

        if(user == null)
            return new AuthenticatedUserDto();

        switch (dataToken.getUserType()){

            case Student:
                Students students = (Students) user;
                return new AuthenticatedUserDto(students.getDocument(), dataToken.getUserType(), students.getDocumentType());

            case Teacher:
                Teachers teachers = (Teachers) user;
                return new AuthenticatedUserDto(teachers.getDocument(), dataToken.getUserType(), teachers.getDocumentType());

            default:
                return new AuthenticatedUserDto();
        }
    }

    private Object getUser(AuthenticatedUserDto userDto){
        switch (userDto.getUserType()){

            case Student:
                return studentsRepository.findByDocument(userDto.getDocument());

            case Teacher:
                return teachersRepository.findByDocument(userDto.getDocument());

            default:
                return null;
        }
    }

    @Override
    public ResponseDto<UserInfoDto> getUserInfo(String token) {

        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);
        Object user = getUser(userAuthenticated);

        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDto result =  objectMapper.convertValue(user, UserInfoDto.class);

        return new ResponseDtoBuilder<UserInfoDto>()
                .setDescription("Test")
                .setResult(result)
                .setCode(200)
                .createResponseDto();
    }

    @Override
    public ResponseDto<UserStatsDto> getUserStats(String token) {

        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);

        StudentStats studentStats = statsRepository.findByDocument(userAuthenticated.getDocument());

        if(studentStats == null){
            log.error("The record wasn't found");
            return null;
        }

        List<StudentChallenges> studentChallengesList = studentStats.getStudentChallengesList();
        studentStats.setStudentChallengesList(new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();
        UserStatsDto userStatsDto = objectMapper.convertValue(studentStats, UserStatsDto.class);

        List<StudentChallengesDto> studentChallengesDtos = new ArrayList<>();
        for (StudentChallenges challenge: studentChallengesList) {
            studentChallengesDtos
                    .add(new StudentChallengesDto(challenge.getCurrentPoints(), challenge.getIdChallenge().getId(),
                            challenge.getIdChallenge().getIdAction().getIdTribe().getId()));
        }
        userStatsDto.setStudentChallenges(studentChallengesDtos);

        return  new ResponseDto<>("Stats returned successfully", 200, userStatsDto);
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
