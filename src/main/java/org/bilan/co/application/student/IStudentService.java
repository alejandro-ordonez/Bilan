package org.bilan.co.application.student;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.domain.dtos.student.StudentDto;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.enums.UserType;

public interface IStudentService {
    StudentDashboardDto getStudentStatsRecord(String document);
    ResponseDto<StudentDashboardDto> getStudentStatsDashboard(String document);
    ResponseDto<PagedResponse<StudentDto>> getStudents(int nPage, String partialDocument, String jwt);
}
