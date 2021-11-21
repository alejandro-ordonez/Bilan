package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.teacher.ITeacherService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.college.ClassRoomStats;
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

    @PreAuthorize("hasAuthority('TEACHER')")
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

        return ResponseEntity.ok(null);
    }
}
