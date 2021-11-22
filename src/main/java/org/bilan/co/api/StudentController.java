package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.student.StudentService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StudentService studentService;

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> getDashBoardInfo(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        AuthenticatedUserDto authenticatedUserDto = jwtTokenUtil.getInfoFromToken(jwt);

        ResponseDto<StudentDashboardDto> result = studentService.getStudentStatsDashboard(authenticatedUserDto.getDocument());

        return ResponseEntity.status(result.getCode()).body(result);
    }

    @GetMapping("/dashboard/single")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> getDashBoardInfoSingle(@RequestParam("document") String document){

        ResponseDto<StudentDashboardDto> result = studentService.getStudentStatsDashboard(document);
        
        return ResponseEntity.status(result.getCode()).body(result);
    }

}
