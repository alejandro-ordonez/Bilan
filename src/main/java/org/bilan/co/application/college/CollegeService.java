package org.bilan.co.application.college;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.course.GradeCoursesDto;
import org.bilan.co.domain.dtos.course.ICourseProjection;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.bilan.co.infraestructure.persistance.CoursesRepository;
import org.dozer.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CollegeService implements ICollegeService {

    private final CollegesRepository collegesRepository;
    private final CoursesRepository coursesRepository;
    private final Mapper mapper;

    public CollegeService(CollegesRepository collegesRepository, CoursesRepository coursesRepository,
                          Mapper mapper) {
        this.collegesRepository = collegesRepository;
        this.coursesRepository = coursesRepository;
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
    public ResponseDto<List<GradeCoursesDto>> getGradesAndCourses(Integer collegeId) {
        List<ICourseProjection> records = coursesRepository.getCoursesAndGradeWithStudentsByCollege(collegeId);

        List<GradeCoursesDto> courses = records.stream()
                .collect(Collectors.groupingBy(ICourseProjection::getGrade))
                .entrySet()
                .stream()
                .map(entry -> Factories.newGradeCourseDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new ResponseDto<>(String.format("List of grades and courses for %d", collegeId), 200, courses);
    }
}
