package org.bilan.co.application.college;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.CourseDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.bilan.co.infraestructure.persistance.CoursesRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.dozer.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CollegeService implements ICollegeService {

    private final CollegesRepository collegesRepository;
    private final CoursesRepository coursesRepository;
    private final TeachersRepository teachersRepository;
    private final Mapper mapper;

    public CollegeService(CollegesRepository collegesRepository, CoursesRepository coursesRepository,
                          TeachersRepository teachersRepository, Mapper mapper) {
        this.collegesRepository = collegesRepository;
        this.coursesRepository = coursesRepository;
        this.teachersRepository = teachersRepository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "colleges")
    public ResponseDto<List<CollegeDto>> findCollegesByState(Integer stateMunId) {
        List<CollegeDto> colleges = collegesRepository.getColleges(stateMunId);
        if (colleges.isEmpty()) {
            return new ResponseDto<>("Resource not found", 404, colleges);
        }
        return new ResponseDto<>("Colleges retrieved successfully", 200, colleges);
    }

    @Override
    public ResponseDto<CollegeDashboardDto> statistics(AuthenticatedUserDto user) {
        return this.teachersRepository.findById(user.getDocument())
                .map(teachers -> this.collegesRepository.collegeByCampusCodeDane(teachers.getCodDaneSede()))
                .map(colleges -> collegesRepository.statistics(colleges.getId()))
                .map(Factories::newCollegeDashboard)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
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
