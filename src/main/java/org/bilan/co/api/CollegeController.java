package org.bilan.co.api;

import org.bilan.co.application.college.ICollegeService;
import org.bilan.co.domain.dtos.CollegeDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/college-info")
public class CollegeController {

    @Autowired
    private ICollegeService collegeService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<CollegeDto>>> getColleges() {
        return ResponseEntity.ok(collegeService.getColleges());
    }

    @GetMapping("/grades-courses")
    public ResponseEntity<ResponseDto<GradeCoursesDto>> getGradesAndCourses(){
        return ResponseEntity.ok(collegeService.getGradesAndCourses());
    }



}
