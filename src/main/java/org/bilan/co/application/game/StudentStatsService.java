package org.bilan.co.application.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.IActionsService;
import org.bilan.co.domain.dtos.*;
import org.bilan.co.domain.entities.*;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StudentStatsService implements IStudentStatsService{


    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private SessionsRepository sessionsRepository;
    @Autowired
    private TribesRepository tribesRepository;
    @Autowired
    private ResolvedAnswerByRepository resolvedAnswerByRepository;
    @Autowired
    private AnswersRepository answersRepository;
    @Autowired
    private ChallengesRepository challengesRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private IActionsService actionsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public ResponseDto<GameStatsDto> getUserStats(String token) {

        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);

        StudentStats studentStats = statsRepository.findByDocument(userAuthenticated.getDocument());

        if(studentStats == null){
            log.warn("The record wasn't found a new one will be created");

            Students student = studentsRepository.findByDocument(userAuthenticated.getDocument());

            studentStats = new StudentStats();
            studentStats.setIdStudent(student);
            student.setStudentStats(studentStats);
            studentsRepository.save(student);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        //The stored stats are mapped
        GameStatsDto gameStatsDto = objectMapper.convertValue(studentStats, GameStatsDto.class);
        gameStatsDto.setTribesPoints(new ArrayList<>());

        List<ActionsPoints> actionsPoints = sessionsRepository.getActionsPoints(userAuthenticated.getDocument());

        Map<Integer, List<ActionsPoints>> tribesPointMap =  actionsPoints.stream().collect(Collectors.groupingBy(ActionsPoints::getTribeId));

        tribesPointMap.forEach((key, value) ->
                gameStatsDto.getTribesPoints()
                .add(new TribesPoints(key, value.stream().map(ActionsPoints::getScore).reduce(Long::sum).orElse(0L), value)));

        return new ResponseDto<>("Stats returned successfully", 200, gameStatsDto);
    }

    @Transactional
    @Override
    public ResponseDto<String> updateUserStats(UpdateStatsDto updateStats, String token) {
        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);

        StudentStats studentStats = statsRepository.findByDocument(userAuthenticated.getDocument());

        studentStats.setLastTotemUpdate(new Date());
        studentStats.setCurrentCycle(updateStats.getCurrentCycle());
        studentStats.setCurrentSpirits(updateStats.getCurrentSpirits());
        studentStats.setAnalyticalTotems(updateStats.getAnalyticalTotems());
        studentStats.setCriticalTotems(updateStats.getCriticalTotems());
        studentStats.setGeneralTotems(updateStats.getGeneralTotems());
        studentStats.setTribesBalance(updateStats.getTribesBalance());

        statsRepository.save(studentStats);

        if(updateStats.getActionsPoints() == null){
            updateStats.setActionsPoints(new ArrayList<>());
        }

        updateStats.getActionsPoints().forEach(update -> saveSessions(userAuthenticated.getDocument(), update));

        return new ResponseDto<>("The update was applied successfully", 200, "Ok");
    }


    private void saveSessions(String document, UpdateActionsPointsDto update){

        Students students = new Students();
        students.setDocument(document);

        Sessions sessions = new Sessions();
        sessions.setScore(update.getScore());
        sessions.setStudents(students);

        Challenges challenges = new Challenges();
        challenges.setId(update.getChallengeId());
        sessions.setChallenges(challenges);

        Tribes tribes = new Tribes();
        tribes.setId(update.getTribeId());
        sessions.setTribeId(tribes);

        Actions actions = new Actions();
        actions.setId(update.getActionId());
        sessions.setActions(actions);

        sessionsRepository.save(sessions);

        List<ResolvedAnswerBy> results= update.getAnswerRecords().stream().map(answerRecord ->  getResolvedAnswerBy(students, answerRecord, actions, sessions))
                .collect(Collectors.toList());

        sessions.setResolvedAnswerBy(results);
        sessionsRepository.save(sessions);

        resolvedAnswerByRepository.saveAll(results);
    }

    private ResolvedAnswerBy getResolvedAnswerBy(Students students, AnswerRecordDto answers, Actions actions
            , Sessions sessions){

        ResolvedAnswerBy resolvedAnswerBy = new ResolvedAnswerBy();

        resolvedAnswerBy.setCreatedAt(new Date());
        resolvedAnswerBy.setSessions(sessions);

        Questions questions = new Questions();
        questions.setId(answers.getQuestionId());

        Answers answerEntity = new Answers();
        answerEntity.setId(answers.getAnswerId());

        resolvedAnswerBy.setIdAnswer(answerEntity);
        resolvedAnswerBy.setIdQuestion(questions);

        return resolvedAnswerBy;
    }
}
