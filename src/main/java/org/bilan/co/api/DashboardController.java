package org.bilan.co.api;


import org.bilan.co.application.dashboards.IDashboardService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GeneralDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GradeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.cache.annotation.Cacheable;
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

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'ADMIN')")
    @GetMapping
    @Cacheable(Constants.GENERAL_STATISTICS)
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> statistics() {
        return ResponseEntity.ok(dashboardService.statistics());
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'ADMIN')")
    @GetMapping("/state")
    @Cacheable(value = Constants.STATE_STATISTICS, key = "#state")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> stateStatistics(@RequestParam String state) {
        return ResponseEntity.ok(dashboardService.stateStatistics(state));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'ADMIN')")
    @GetMapping("/municipality")
    @Cacheable(value = Constants.MUNICIPALITY_STATISTICS, key = "#munId + '-' + #page")
    public ResponseEntity<ResponseDto<GeneralDashboardDto>> munStatistics(@RequestParam Integer munId,
                                                                          @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(dashboardService.municipalityStatistics(munId, PageRequest.of(Math.max(0, page),
                Constants.MAX_SIZE_PAGE)));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'ADMIN')")
    @GetMapping("/college")
    @Cacheable(value = Constants.COLLEGE_STATISTICS, key = "#collegeId")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> collegeStatistics(@RequestParam("collegeId") int collegeId) {
        return ResponseEntity.ok(dashboardService.collegeStatistics(collegeId));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER', 'ADMIN')")
    @GetMapping("/grade")
    @Cacheable(value = Constants.GRADE_STATISTICS, key = "#collegeId + '-' + #grade + '-' + #courseId")
    public ResponseEntity<ResponseDto<GradeDashboardDto>> courseGradeStatistics(@RequestParam int collegeId,
                                                                                @RequestParam String grade,
                                                                                @RequestParam String courseId) {
        return ResponseEntity.ok(dashboardService.courseGradeStatistics(collegeId, grade, courseId));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER', 'STUDENT', 'ADMIN')")
    @GetMapping("/student")
    @Cacheable(value = Constants.STUDENT_STATISTICS, key = "#document")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> studentStatistics(@RequestParam String document) {
        return ResponseEntity.ok(dashboardService.studentStatistics(document));
    }
}
