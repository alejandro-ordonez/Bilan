package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.teacher.ITeacherService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.college.ClassRoomStats;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.teacher.TeacherDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;


    @PreAuthorize("hasAuthority('TEACHER, DIRECT_TEACHER')")
    @PutMapping()
    public ResponseEntity<ResponseDto<String>> updateTeacher(@RequestBody TeacherDto teacherDto){
        return ResponseEntity.ok(this.teacherService.updateTeacher(teacherDto));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<TeacherDto>> getTeacher(@RequestParam("document") String document){
        ResponseDto<TeacherDto> res = this.teacherService.getTeacher(document);
        return ResponseEntity.status(res.getCode()).body(res);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<PagedResponse<TeacherDto>>> getTeachers(@RequestParam("page") Integer nPage,
                                                                              @RequestParam(value = "partialDocument", required = false) String partialDocument,
                                                                              @RequestHeader(Constants.AUTHORIZATION) String jwt){
        ResponseDto<PagedResponse<TeacherDto>> res = this.teacherService.getTeachers(nPage, partialDocument, jwt);
        return ResponseEntity.status(res.getCode()).body(res);
    }

    @PostMapping("/enroll")
    public ResponseEntity<ResponseDto<String>> enrollTeacher(@RequestBody EnrollmentDto enrollmentDto){
            return ResponseEntity.ok(teacherService.enroll(enrollmentDto));
    }

    @GetMapping("/class-rooms")
    public ResponseEntity<ResponseDto<List<ClassRoomDto>>> getClassRooms(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        return ResponseEntity.ok(teacherService.getClassrooms(jwt));
    }

    @GetMapping("/class-rooms/info")
    public ResponseEntity<ResponseDto<ClassRoomStats>> getClassRoomStats(
            @RequestParam("classRoomId") Integer classRoomId){

        return ResponseEntity.ok(teacherService.getClassroomStats(classRoomId));
    }
}
