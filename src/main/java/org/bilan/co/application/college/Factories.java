package org.bilan.co.application.college;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.course.GradeCoursesDto;
import org.bilan.co.domain.dtos.course.GradeCoursesDto.CourseDto;
import org.bilan.co.domain.dtos.course.ICourseProjection;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    public static GradeCoursesDto newGradeCourseDto(String grade, List<ICourseProjection> coursesProjection) {
        GradeCoursesDto gradeCoursesDto = new GradeCoursesDto();
        gradeCoursesDto.setGrade(grade);
        List<CourseDto> courses = coursesProjection.stream().map(course -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setName(course.getName());
            return courseDto;
        }).collect(Collectors.toList());
        gradeCoursesDto.setCourses(courses);
        return gradeCoursesDto;
    }
}
