package org.bilan.co.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.*;
import org.bilan.co.domain.entities.*;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
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
        studentStats.setTribesBalance(updateStats.getTribesBalance());

        statsRepository.save(studentStats);

        updateStats.getActionsPoints().forEach(update -> getSession(userAuthenticated.getDocument(), update));

        return new ResponseDto<>("The update was applied successfully", 200, "Ok");
    }


    private void getSession(String document, UpdateActionsPointsDto update){

        Students students = new Students();
        students.setDocument(document);

        Actions actions = new Actions();
        actions.setId(update.getId());

        Sessions sessions = new Sessions();
        sessions.setScore(update.getScore());
        sessions.setStudents(students);
        sessions.setActions(actions);

        sessionsRepository.save(sessions);

        List<ResolvedAnswerBy> results= update.getAnswerRecords().stream().map(answerRecord ->  getResolvedAnswerBy(students, answerRecord, actions, sessions))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        sessions.setResolvedAnswerBy(results);

        resolvedAnswerByRepository.saveAll(results);
    }

    private List<ResolvedAnswerBy> getResolvedAnswerBy(Students students, AnswerRecordDto answers, Actions actions
            , Sessions sessions){

    return answers.getAnswers().stream().map(answer -> {
            ResolvedAnswerBy resolvedAnswerBy = new ResolvedAnswerBy();

            Challenges challenges = challengesRepository.getById(answers.getChallengeId());

            resolvedAnswerBy.setIdChallenge(challenges);
            resolvedAnswerBy.setIdStudent(students);
            resolvedAnswerBy.setActions(actions);
            resolvedAnswerBy.setCreatedAt(new Date());
            resolvedAnswerBy.setSessions(sessions);

            Questions questions = questionsRepository.getById(answer.getQuestionId());

            Answers answerEntity = answersRepository.getById(answer.getAnswerId());

            resolvedAnswerBy.setIdAnswer(answerEntity);
            resolvedAnswerBy.setIdQuestion(questions);
            return resolvedAnswerBy;
        }).collect(Collectors.toList());
    }
}
