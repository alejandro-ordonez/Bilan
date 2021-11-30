package org.bilan.co.application.dashboards;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;

public interface IDashboardService {

    ResponseDto<CollegeDashboardDto> statistics(AuthenticatedUserDto user);
}
