package org.bilan.co.domain.dtos.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class GradeCoursesDto {
    private String grade;
    private List<CourseDto> courses;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class CourseDto {
        private Integer id;
        private String name;
    }
}
