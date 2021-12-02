package org.bilan.co.application.dashboards;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;

public interface IDashboardService {

    ResponseDto<CollegeDashboardDto> collegeStatistics(AuthenticatedUserDto user);

    ResponseDto<CollegeDashboardDto> collegeStatistics(String codeDane);

    ResponseDto<GovernmentDashboardDto> governmentStatistics();

    ResponseDto<GovernmentDashboardDto> stateStatistics(String state);

    ResponseDto<GovernmentDashboardDto> studentStatistics(Integer studentId);

    ResponseDto<GovernmentDashboardDto> collegeGradeStatistics(String codeDane, String grade, Integer courseId);
}
