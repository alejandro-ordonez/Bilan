package org.bilan.co.application.student;

import org.bilan.co.domain.dtos.student.StudentStatsRecord;

public interface IStudentService {
    StudentStatsRecord getStudentStatsRecord(String document);
}
