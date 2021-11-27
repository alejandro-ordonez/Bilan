package org.bilan.co.application.college;

import org.bilan.co.domain.dtos.CollegeDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;

import java.util.List;

public interface ICollegeService {

    ResponseDto<List<CollegeDto>> findCollegesByState(Integer stateMun);

    ResponseDto<GradeCoursesDto> getGradesAndCourses();
}
