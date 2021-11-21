package org.bilan.co.application.teacher;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;

import java.util.List;

public interface ITeacherService {
    ResponseDto<String> enroll(EnrollmentDto enrollmentDto);
    ResponseDto<List<ClassRoomDto>> getClassrooms(String jwt);
}
