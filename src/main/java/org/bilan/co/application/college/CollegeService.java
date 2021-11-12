package org.bilan.co.application.college;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.CollegeDto;
import org.bilan.co.domain.dtos.CourseDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.bilan.co.infraestructure.persistance.CoursesRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CollegeService implements ICollegeService{

    @Autowired
    private CollegesRepository collegesRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public ResponseDto<List<CollegeDto>> getColleges() {
        log.info("Colleges requested");
        List<CollegeDto> colleges = collegesRepository.getColleges();
        return new ResponseDto<>("Colleges retrieved successfully", 200, colleges);
    }

    @Override
    public ResponseDto<GradeCoursesDto> getGradesAndCourses() {
        log.info("Grades and courses requested");
        //Consider creating a table to store the grades.
        List<String> grades = new ArrayList<>();
        grades.add("10");
        grades.add("11");

        GradeCoursesDto response = new GradeCoursesDto();
        response.setGrades(grades);


        List<CourseDto> courses = coursesRepository.findAll()
                .stream()
                .map(c -> mapper.map(c, CourseDto.class))
                .collect(Collectors.toList());

        response.setCourses(courses);

        return new ResponseDto<>("Grades and courses retrieved successfully", 200, response);
    }

}
