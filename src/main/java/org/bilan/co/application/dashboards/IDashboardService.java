package org.bilan.co.application.dashboards;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;

public interface IDashboardService {

    ResponseDto<GovernmentDashboardDto> govStatistics();

    ResponseDto<GovernmentDashboardDto> govStateStatistics(String state);

    ResponseDto<GovernmentDashboardDto> govMunicipalityStatistics(Integer munId);

    ResponseDto<GovernmentDashboardDto> govCollegeStatistics(Integer collegeId);



   /* ResponseDto<GovernmentDashboardDto> studentStatistics(Integer studentId);

    ResponseDto<GovernmentDashboardDto> collegeGradeStatistics(String codeDane, String grade, Integer courseId);

    ResponseDto<GovernmentDashboardDto> municipalityStatistics(Integer municipality);

    ResponseDto<CollegeDashboardDto> collegeStatistics(AuthenticatedUserDto user);

    ResponseDto<CollegeDashboardDto> collegeStatistics(String codeDane);
    */
}
