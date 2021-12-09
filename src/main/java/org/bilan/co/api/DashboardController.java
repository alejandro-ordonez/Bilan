package org.bilan.co.api;


import org.bilan.co.application.dashboards.IDashboardService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GeneralDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GradeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.data.domain.PageRequest;
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

    @PreAuthorize("hasAuthority('MIN_USER')")
    @GetMapping("/government/statistics")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> govStatistics() {
        return ResponseEntity.ok(dashboardService.govStatistics());
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU')")
    @GetMapping("/government/state/statistics")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> govStateStatistics(@RequestParam String state) {
        return ResponseEntity.ok(dashboardService.govStateStatistics(state));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU')")
    @GetMapping("/government/municipality/statistics")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> govMunStatistics(@RequestParam Integer munId,
                                                                             @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(dashboardService.govMunicipalityStatistics(munId, PageRequest.of(Math.max(0, page),
                Constants.MAX_SIZE_PAGE)));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER')")
    @GetMapping("/government/college/statistics")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> govCollegeStatistics(@RequestParam Integer collegeId) {
        return ResponseEntity.ok(dashboardService.govCollegeStatistics(collegeId));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER')")
    @GetMapping("/government/grade/statistics")
    public ResponseEntity<ResponseDto<GradeDashboardDto>> govCourseGradeStatistics(@RequestParam Integer collegeId,
                                                                                   @RequestParam String grade,
                                                                                   @RequestParam Integer courseId) {
        return ResponseEntity.ok(dashboardService.govCourseGradeStatistics(collegeId, grade, courseId));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER', 'STUDENT')")
    @GetMapping("/government/student/statistics")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> govStudentStatistics(@RequestParam String document) {
        return ResponseEntity.ok(dashboardService.govStudentStatistics(document));
    }
}
