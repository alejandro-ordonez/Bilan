package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.student.IStudentService;
import org.bilan.co.application.student.StudentService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.domain.dtos.student.StudentDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IStudentService studentService;

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> getDashBoardInfo(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        AuthenticatedUserDto authenticatedUserDto = jwtTokenUtil.getInfoFromToken(jwt);

        ResponseDto<StudentDashboardDto> result = studentService.getStudentStatsDashboard(authenticatedUserDto.getDocument());

        return ResponseEntity.status(result.getCode()).body(result);
    }


    @GetMapping()
    public ResponseEntity<ResponseDto<PagedResponse<StudentDto>>> getStudents(@RequestParam("page") Integer nPage,
                                                                              @RequestParam(value = "partialDocument", required = false) String partialDocument,
                                                                              @RequestHeader(Constants.AUTHORIZATION) String jwt){
        ResponseDto<PagedResponse<StudentDto>> result = studentService.getStudents(nPage, partialDocument, jwt);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @GetMapping("/dashboard/single")
    public ResponseEntity<ResponseDto<StudentDashboardDto>> getDashBoardInfoSingle(@RequestParam("document") String document){

        ResponseDto<StudentDashboardDto> result = studentService.getStudentStatsDashboard(document);

        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('TEACHER, DIRECT_TEACHER')")
    public ResponseEntity<ResponseDto<String>> updateStudent(@RequestBody StudentDto studentDto){
        ResponseDto<String> response = this.studentService.updateStudent(studentDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
