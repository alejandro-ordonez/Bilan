package org.bilan.co.api;

import org.bilan.co.application.college.ICollegeService;
import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/college")
public class CollegeController {


    private final ICollegeService collegeService;
    private final JwtTokenUtil jwtTokenUtil;

    public CollegeController(ICollegeService collegeService, JwtTokenUtil jwtTokenUtil) {
        this.collegeService = collegeService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CollegeDto>>> findCollegesByState(@RequestParam Integer stateMunId) {
        return ResponseEntity.ok(collegeService.findCollegesByState(stateMunId));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/statistics")
    public ResponseEntity<ResponseDto<CollegeDashboardDto>> statistics(@RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(collegeService.statistics(user));
    }

    @GetMapping("/grades-courses")
    public ResponseEntity<ResponseDto<GradeCoursesDto>> getGradesAndCourses() {
        return ResponseEntity.ok(collegeService.getGradesAndCourses());
    }
}
