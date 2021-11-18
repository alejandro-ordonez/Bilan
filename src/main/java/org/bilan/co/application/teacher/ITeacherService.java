package org.bilan.co.application.teacher;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;

public interface ITeacherService {
    ResponseDto<String> enroll(EnrollmentDto enrollmentDto);
}
