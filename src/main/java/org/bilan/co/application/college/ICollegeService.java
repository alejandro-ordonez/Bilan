package org.bilan.co.application.college;

import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.course.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;

import java.util.List;

public interface ICollegeService {

    ResponseDto<List<CollegeDto>> findCollegesByState(Integer stateMun);

    ResponseDto<List<GradeCoursesDto>> getGradesAndCourses(Integer collegeId);

}
