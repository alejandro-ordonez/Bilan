package org.bilan.co.application.student;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.infraestructure.persistance.EvidenceRepository;
import org.bilan.co.infraestructure.persistance.QuestionsRepository;
import org.bilan.co.infraestructure.persistance.ResolvedAnswerByRepository;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentService implements IStudentService{

    @Autowired
    private EvidenceRepository evidenceRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private ResolvedAnswerByRepository resolvedAnswerByRepository;
    @Autowired
    private TribesRepository tribesRepository;

    @Override
    public StudentDashboardDto getStudentStatsRecord(String document) {

        StudentDashboardDto studentStatsRecord = new StudentDashboardDto();
        studentStatsRecord.setDocument(document);

        long totalQuestions = questionsRepository.count();
        Integer totalCheckedQuestions = resolvedAnswerByRepository.getQuestionsCompleted(document);

        long totalActivities = tribesRepository.count()*3;
        Integer resolved = evidenceRepository.findUploadedAndEvaluated(document);

        float progress = (float)(totalCheckedQuestions+resolved)/(float) (totalQuestions+totalActivities);
        studentStatsRecord.setProgressActivities(progress);

        return studentStatsRecord;
    }

    @Override
    public ResponseDto<StudentDashboardDto> getStudentStatsDashboard(String document) {
        return new ResponseDto<>("Dashboard retrieved", 200, getStudentStatsRecord(document));
    }
}
