package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping
    private ResponseEntity<ResponseDto<StudentDashboardDto>> getDashBoardInfo(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        return null;
    }
}
