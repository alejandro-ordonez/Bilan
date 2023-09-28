package org.bilan.co.application.dashboards;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GeneralDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GradeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;
import org.springframework.data.domain.PageRequest;

public interface IDashboardService {

    ResponseDto<GeneralDashboardDto> statistics();

    ResponseDto<GeneralDashboardDto> stateStatistics(String state);

    ResponseDto<GeneralDashboardDto> municipalityStatistics(Integer munId, PageRequest page);

    ResponseDto<CollegeDashboardDto> collegeStatistics(int collegeId);

    ResponseDto<GradeDashboardDto> courseGradeStatistics(int collegeId, String grade, String courseId);

    ResponseDto<StudentDashboardDto> studentStatistics(String document);
}
