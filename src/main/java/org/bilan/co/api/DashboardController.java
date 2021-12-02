package org.bilan.co.api;


import org.bilan.co.application.dashboards.IDashboardService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/government/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> govStatistics() {
        return ResponseEntity.ok(dashboardService.govStatistics());
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/state/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> govStateStatistics(@RequestParam String state) {
        return ResponseEntity.ok(dashboardService.govStateStatistics(state));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/municipality/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> govMunStatistics(@RequestParam Integer munId) {
        return ResponseEntity.ok(dashboardService.govMunicipalityStatistics(munId));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/college/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> collegeStatistics(@RequestParam Integer collegeId) {
        return ResponseEntity.ok(dashboardService.govCollegeStatistics(collegeId));
    }


    /*


    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/college/statistics")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> collegeStatistics(@RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(dashboardService.collegeStatistics(user));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/municipality/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> stateStatistics(@RequestParam Integer municipality) {
        return ResponseEntity.ok(dashboardService.municipalityStatistics(municipality));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/college/statistics")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> collegeStatistics(@RequestParam String codeCampusDane,
                                                                              @RequestHeader(Constants.AUTHORIZATION) String token) {
        return ResponseEntity.ok(dashboardService.collegeStatistics(codeCampusDane));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/college/grade/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> collegeGradeStatistics(@RequestParam String codeDane,
                                                                                      @RequestParam String grade,
                                                                                      @RequestParam Integer courseId) {
        return ResponseEntity.ok(dashboardService.collegeGradeStatistics(codeDane, grade, courseId));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/government/student/statistics")
    public ResponseEntity<ResponseDto<GovernmentDashboardDto>> studentStatistics(@RequestParam Integer studentId) {
        return ResponseEntity.ok(dashboardService.studentStatistics(studentId));
    }*/
}
