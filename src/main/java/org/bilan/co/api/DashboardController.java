package org.bilan.co.api;


import org.bilan.co.application.dashboards.IDashboardService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.college.GovernmentDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final IDashboardService dashboardService;
    private final JwtTokenUtil jwtTokenUtil;

    public DashboardController(IDashboardService dashboardService, JwtTokenUtil jwtTokenUtil) {
        this.dashboardService = dashboardService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/college/statistics")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> statistics(@RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(dashboardService.statistics(user));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> governmentStatistics(@RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(dashboardService.governmentStatistics());
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/state/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> stateStatistics(@RequestParam String state,
                                                                               @RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(dashboardService.stateStatistics(state));
    }
}
