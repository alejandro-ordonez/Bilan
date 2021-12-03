package org.bilan.co.application.dashboards;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;

public interface IDashboardService {

    ResponseDto<GovernmentDashboardDto> govStatistics();

    ResponseDto<GovernmentDashboardDto> govStateStatistics(String state);

    ResponseDto<GovernmentDashboardDto> govMunicipalityStatistics(Integer munId);

    ResponseDto<GovernmentDashboardDto> govCollegeStatistics(Integer collegeId);

    ResponseDto<GovernmentDashboardDto> govCourseGradeStatistics(Integer collegeId, String grade, Integer courseId);

    ResponseDto<StudentDashboardDto> govStudentStatistics(String document);
}
