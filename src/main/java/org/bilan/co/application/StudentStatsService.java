package org.bilan.co.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.StudentChallengesDto;
import org.bilan.co.domain.dtos.UserStatsDto;
import org.bilan.co.domain.entities.Challenges;
import org.bilan.co.domain.entities.StudentStats;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.infraestructure.persistance.ChallengesRepository;
import org.bilan.co.infraestructure.persistance.StatsRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class StudentStatsService implements IStudentStatsService{


    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private ChallengesRepository challengesRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    //TODO: Pending to change
    @Override
    public ResponseDto<UserStatsDto> getUserStats(String token) {

        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);

        StudentStats studentStats = statsRepository.findByDocument(userAuthenticated.getDocument());

        //Checks if there's a student stats records to be updated. If not, there will be inserted a new one with default values
        if (studentStats == null) {
            log.warn("The record wasn't found a new one will be created");
            Students students = studentsRepository.findByDocument(userAuthenticated.getDocument());
            studentStats = StudentStats.getDefault();
            students.setStudentStats(studentStats);
            studentStats.setIdStudent(students);
            studentsRepository.save(students);

        }

        List<StudentActions> studentChallengesList = studentStats.getStudentChallengesList();
        studentStats.setStudentChallengesList(new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();
        UserStatsDto userStatsDto = objectMapper.convertValue(studentStats, UserStatsDto.class);

        List<StudentChallengesDto> studentChallengesDtos = new ArrayList<>();
        for (StudentActions studentChallenge : studentChallengesList) {

            //Challenges challenge = studentChallenge.getIdChallenge();

            //studentChallengesDtos.add(new StudentChallengesDto(studentChallenge.getCurrentPoints(),
            //        challenge.getId(), challenge.getIdAction().getId(),challenge.getIdAction().getIdTribe().getId()));

        }
        userStatsDto.setStudentChallenges(studentChallengesDtos);

        return new ResponseDto<>("Stats returned successfully", 200, userStatsDto);
    }

    @Override
    public ResponseDto<String> updateUserStats(UserStatsDto userStatsDto, String token) {
        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);

        StudentStats studentStats = statsRepository.findByDocument(userAuthenticated.getDocument());

        studentStats.setCurrentSpirits(userStatsDto.getCurrentSpirits());
        studentStats.setAnalyticalTotems(userStatsDto.getAnalyticalTotems());
        studentStats.setCriticalTotems(userStatsDto.getCriticalTotems());
        studentStats.setCurrentCycle(userStatsDto.getCurrentCycle());
        studentStats.setLastTotemUpdate(new Date());

        //Iterates over each element in the update dto
        for(StudentChallengesDto studentChallengesDto: userStatsDto.getStudentChallenges()){
            //Filters the record to be updated by challenge. The other values are ignored as the challenge must
            //be already referenced with his tribe. In case it's not the id must be wrong
            //Optional<StudentActions> studentChallenges = studentStats.getStudentChallengesList().stream()
            //        .filter(challenge -> challenge.getIdChallenge().getId() == studentChallengesDto.getChallengeId())
            //        .findFirst();

            // If it is not found in the records of StudentChallenges then a new one is created base in an existing challenge
            //if(!studentChallenges.isPresent()){

                Optional<Challenges> challenge = challengesRepository.findById(studentChallengesDto.getChallengeId());
                //If the challenge is not found will be assumed that the id is incorrect.
                if(!challenge.isPresent()){
                    String message = "The challenge trying to be updated doesn't exists in the database, make sure the id is correct";
                    log.error(message);
                    return new ResponseDto<>(message, 404, "Failed");
                }

                //If it exists a new reference will be created.
                StudentActions newStudentChallenge = new StudentActions();
                //Reference to the challenge that belongs this record
                //newStudentChallenge.setIdChallenge(challenge.get());
                //Reference to the stats that this record belongs
                newStudentChallenge.setIdStudentStat(studentStats);
                //Updates the new points
                newStudentChallenge.setCurrentPoints(studentChallengesDto.getCurrentPoints());
                //Adds a reference to solve the relationship, JPA will take care of the rest.
                //challenge.get().setStudentChallenges(newStudentChallenge);
                //The new record is added to the list.
                studentStats.getStudentChallengesList().add(newStudentChallenge);
                break;
            //}

           // studentChallenges.get().setCurrentPoints(studentChallengesDto.getCurrentPoints());

        }
        return new ResponseDto<>("The update was applied successfully", 200, "Ok");
    }


}
