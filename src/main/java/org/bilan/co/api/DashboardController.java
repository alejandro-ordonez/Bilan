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
@RequestMapping("/dashboard/statistics")
public class DashboardController {

    private final IDashboardService dashboardService;
    private final JwtTokenUtil jwtTokenUtil;

    public DashboardController(IDashboardService dashboardService, JwtTokenUtil jwtTokenUtil) {
        this.dashboardService = dashboardService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PreAuthorize("hasAuthority('MIN_USER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> statistics() {
        return ResponseEntity.ok(dashboardService.statistics());
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU')")
    @GetMapping("/state")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> stateStatistics(@RequestParam String state) {
        return ResponseEntity.ok(dashboardService.stateStatistics(state));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU')")
    @GetMapping("/municipality")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> munStatistics(@RequestParam Integer munId,
                                                                          @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(dashboardService.municipalityStatistics(munId, PageRequest.of(Math.max(0, page),
                Constants.MAX_SIZE_PAGE)));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER')")
    @GetMapping("/college")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> collegeStatistics(@RequestParam("codDane") String codDane) {
        return ResponseEntity.ok(dashboardService.collegeStatistics(codDane));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER')")
    @GetMapping("/grade")
    public ResponseEntity<ResponseDto<GradeDashboardDto>> courseGradeStatistics(@RequestParam Integer collegeId,
                                                                                @RequestParam String grade,
                                                                                @RequestParam Integer courseId) {
        return ResponseEntity.ok(dashboardService.courseGradeStatistics(collegeId, grade, courseId));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER', 'STUDENT')")
    @GetMapping("/student")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> studentStatistics(@RequestParam String document) {
        return ResponseEntity.ok(dashboardService.studentStatistics(document));
    }
}
