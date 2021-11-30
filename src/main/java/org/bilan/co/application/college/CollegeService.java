package org.bilan.co.application.college;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.college.IModuleDashboard;
import org.bilan.co.domain.dtos.course.GradeCoursesDto;
import org.bilan.co.domain.dtos.course.ICourseProjection;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.bilan.co.infraestructure.persistance.CoursesRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.dozer.Mapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CollegeService implements ICollegeService {

    private final CollegesRepository collegesRepository;
    private final CoursesRepository coursesRepository;
    private final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository;
    private final Mapper mapper;

    public CollegeService(CollegesRepository collegesRepository, CoursesRepository coursesRepository,
                          TeachersRepository teachersRepository, StudentsRepository studentsRepository,
                          Mapper mapper) {
        this.collegesRepository = collegesRepository;
        this.coursesRepository = coursesRepository;
        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
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
                .map(this::summary)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @NotNull
    private CollegeDashboardDto summary(Colleges colleges) {
        List<IModuleDashboard> statistics = collegesRepository.statistics(colleges.getId());
        return Factories.newCollegeDashboard(statistics, colleges.getStudents().size());
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
