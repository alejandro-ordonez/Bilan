package org.bilan.co.application.teacher;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.college.ClassRoomStats;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.teacher.TeacherDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;

import java.util.List;

public interface ITeacherService {
    ResponseDto<String> enroll(EnrollmentDto enrollmentDto);
    ResponseDto<List<ClassRoomDto>> getClassrooms(String jwt);
    ResponseDto<ClassRoomStats> getClassroomStats(Integer classRoomId);
    ResponseDto<TeacherDto> getTeacher(String document);
    ResponseDto<String> updateTeacher(TeacherDto teacherDto);

    ResponseDto<PagedResponse<TeacherDto>> getTeachers(Integer nPage, String partialDocument, String jwt);
}
