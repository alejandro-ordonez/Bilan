package org.bilan.co.domain.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GradeCoursesDto {
    List<String> grades;
    List<CourseDto> courses;
}
