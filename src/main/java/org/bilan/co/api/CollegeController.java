package org.bilan.co.api;

import org.bilan.co.application.college.ICollegeService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.course.GradeCoursesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/college")
public class CollegeController {


    private final ICollegeService collegeService;

    public CollegeController(ICollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CollegeDto>>> findCollegesByState(@RequestParam Integer stateMunId) {
        return ResponseEntity.ok(collegeService.findCollegesByState(stateMunId));
    }

    @PreAuthorize("hasAnyAuthority('MIN_USER', 'SEC_EDU', 'DIRECT_TEACHER', 'TEACHER')")
    @GetMapping("/grades-courses")
    public ResponseEntity<ResponseDto<List<GradeCoursesDto>>> getGradesAndCourses(@RequestParam Integer collegeId) {
        return ResponseEntity.ok(collegeService.getGradesAndCourses(collegeId));
    }
}
