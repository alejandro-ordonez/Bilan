package org.bilan.co.application.student;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;

public interface IStudentService {
    StudentDashboardDto getStudentStatsRecord(String document);
    ResponseDto<StudentDashboardDto> getStudentStatsDashboard(String document);
}
