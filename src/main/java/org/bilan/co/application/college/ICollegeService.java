package org.bilan.co.application.college;

import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;

import java.util.List;

public interface ICollegeService {

    ResponseDto<List<CollegeDto>> findCollegesByState(Integer stateMun);

    ResponseDto<GradeCoursesDto> getGradesAndCourses();

    ResponseDto<CollegeDashboardDto> statistics(AuthenticatedUserDto user);
}
