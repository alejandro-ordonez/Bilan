package org.bilan.co.application.student;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.domain.entities.Evaluation;
import org.bilan.co.domain.entities.Evidences;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.infraestructure.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StudentService implements IStudentService{

    @Autowired
    private EvidenceRepository evidenceRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private ResolvedAnswerByRepository resolvedAnswerByRepository;
    @Autowired
    private TribesRepository tribesRepository;

    @Override
    public StudentDashboardDto getStudentStatsRecord(String document) {

        StudentDashboardDto studentStatsRecord = new StudentDashboardDto();
        studentStatsRecord.setDocument(document);

        Optional<Students> studentQuery = studentsRepository.findById(document);
        if(!studentQuery.isPresent())
            return new StudentDashboardDto();

        studentStatsRecord.setName(studentQuery.get().getName());
        studentStatsRecord.setLastName(studentQuery.get().getLastName());

        long totalQuestions = questionsRepository.count();
        Integer totalCheckedQuestions = resolvedAnswerByRepository.getQuestionsCompleted(document);

        long totalActivities = tribesRepository.count()*3;
        Integer resolved = evidenceRepository.findUploadedAndEvaluated(document);

        float progress = (float)(totalCheckedQuestions+resolved)/(float) (totalQuestions+totalActivities);
        studentStatsRecord.setProgressActivities(progress);

        studentStatsRecord.setTimeInApp(new Random().nextFloat());



        List<Evidences> evidences = evidenceRepository.getEvidencesEvaluated(document);

        Map<Integer, List<Evidences>> evidencesMap = evidences.stream()
                .collect(Collectors.groupingBy(e->e.getTribe().getId()));

        HashMap<String, Integer> activityScores = getActivityScores(evidences);
        //HashMap<String, Integer> gameScore = getGameScore();

        studentStatsRecord.setActivityScore(activityScores);
        //studentStatsRecord.setGameScore(gameScore);

        return studentStatsRecord;
    }


    private HashMap<String, Integer> getActivityScores(List<Evidences> evidences){

        HashMap<String, Integer> activityScores = new HashMap<>();

        Integer cbScore = 0;
        Integer ccScore = 0;
        Integer csScore = 0;
        Integer tribeScore = 0;
        Integer l, m, cn = 0;

        int counter=0;

        for(Evidences evidence : evidences){
            for(Evaluation evaluation : evidence.getEvaluations()){
                ccScore+=evaluation.getCcScore();
                cbScore+=evaluation.getCbScore();
                csScore+=evaluation.getCsScore();
                tribeScore+=evaluation.getTribeScore();
                counter++;
            }
        }

        activityScores.put("CC", ccScore/counter);
        activityScores.put("CP", cbScore/counter);
        activityScores.put("CS", csScore/counter);
        activityScores.put("CE", tribeScore/counter);

        return activityScores;
    }


    @Override
    public ResponseDto<StudentDashboardDto> getStudentStatsDashboard(String document) {
        return new ResponseDto<>("Dashboard retrieved", 200, getStudentStatsRecord(document));
    }
}
