package org.bilan.co.application.teacher;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;
import org.bilan.co.domain.entities.Classroom;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.domain.entities.Tribes;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TeacherService implements ITeacherService{

    @Autowired
    private TeachersRepository teachersRepository;

    @Override
    public ResponseDto<String> enroll(EnrollmentDto enrollmentDto) {

        Optional<Teachers> teacherQuery = teachersRepository.findById(enrollmentDto.getDocument());

        if(!teacherQuery.isPresent())
            return new ResponseDto<>("The teacher was not found make sure the request it's correct",
                    400,
                    "Error");

        Teachers teacher = teacherQuery.get();

        List<Classroom> classroomToEnroll = enrollmentDto.getCoursesToEnroll()
                .stream().map(cr -> {
                    Classroom classroom = new Classroom();
                    classroom.setGrade(cr.getGrade());
                    classroom.setTeacher(teacher);

                    Tribes tribe = new Tribes();
                    tribe.setId(cr.getTribeId());

                    Colleges colleges = new Colleges();
                    colleges.setId(cr.getCollegeId());

                    classroom.setTribe(tribe);
                    classroom.setCollege(colleges);

                    return classroom;
                }).collect(Collectors.toList());

        teacher.getClassrooms().addAll(classroomToEnroll);
        teachersRepository.save(teacher);

        return new ResponseDto<>("Teacher enrolled to courses", 200, "Ok");
    }
}
