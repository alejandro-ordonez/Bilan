package org.bilan.co.application.dashboards;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GeneralDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GradeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;

public interface IDashboardService {

    ResponseDto<GeneralDashboardDto> govStatistics();

    ResponseDto<GeneralDashboardDto> govStateStatistics(String state);

    ResponseDto<GeneralDashboardDto> govMunicipalityStatistics(Integer munId);

    ResponseDto<CollegeDashboardDto> govCollegeStatistics(Integer collegeId);

    ResponseDto<GradeDashboardDto> govCourseGradeStatistics(Integer collegeId, String grade, Integer courseId);

    ResponseDto<StudentDashboardDto> govStudentStatistics(String document);
}