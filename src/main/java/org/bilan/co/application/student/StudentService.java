package org.bilan.co.application.student;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.student.StudentStatsRecord;
import org.bilan.co.infraestructure.persistance.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentService implements IStudentService{

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Override
    public StudentStatsRecord getStudentStatsRecord(String document) {

        StudentStatsRecord studentStatsRecord = new StudentStatsRecord();
        studentStatsRecord.setDocument(document);



        return null;
    }
}
