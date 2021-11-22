package org.bilan.co.application.student;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.infraestructure.persistance.EvidenceRepository;
import org.bilan.co.infraestructure.persistance.QuestionsRepository;
import org.bilan.co.infraestructure.persistance.ResolvedAnswerByRepository;
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

    @Override
    public StudentDashboardDto getStudentStatsRecord(String document) {

        StudentDashboardDto studentStatsRecord = new StudentDashboardDto();
        studentStatsRecord.setDocument(document);

        Long totalQuestions = questionsRepository.count();
        Integer totalCheckedQuestions = resolvedAnswerByRepository.getQuestionsCompleted(document);

        return null;
    }
}
