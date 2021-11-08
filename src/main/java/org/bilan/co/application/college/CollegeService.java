package org.bilan.co.application.college;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.CollegeDto;
import org.bilan.co.domain.dtos.GradeCoursesDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CollegeService implements ICollegeService{

    @Autowired
    private CollegesRepository collegesRepository;

    @Override
    public ResponseDto<List<CollegeDto>> getColleges() {
        log.info("Colleges requested");
        List<CollegeDto> colleges = collegesRepository.getColleges();
        return new ResponseDto<>("Colleges retrieved successfully", 200, colleges);
    }

    @Override
    public ResponseDto<GradeCoursesDto> getGradesAndCourses() {
        log.info("Grades and courses requested");
        return null;
    }

}
