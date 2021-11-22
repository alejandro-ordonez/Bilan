package org.bilan.co.application.teacher;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.student.StudentService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.college.ClassRoomStats;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.infraestructure.persistance.ClassroomRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.dozer.Mapper;
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
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private Mapper mapper;

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

                    Courses course = new Courses();
                    course.setId(cr.getCourseId());

                    classroom.setTribe(tribe);
                    classroom.setCollege(colleges);
                    classroom.setCourse(course);

                    return classroom;
                }).collect(Collectors.toList());

        teacher.getClassrooms().addAll(classroomToEnroll);
        teachersRepository.save(teacher);

        return new ResponseDto<>("Teacher enrolled to courses", 200, "Ok");
    }

    @Override
    public ResponseDto<List<ClassRoomDto>> getClassrooms(String jwt) {

        AuthenticatedUserDto user = jwtUtils.getInfoFromToken(jwt);

        Optional<Teachers> teachers = teachersRepository.findById(user.getDocument());

        if(!teachers.isPresent())
            return new ResponseDto<>("The teacher was not found", 404, null);

        List<ClassRoomDto> classRoomDtos = teachers.get().getClassrooms()
                .stream()
                .map(c -> {
                    ClassRoomDto classRoomDto = new ClassRoomDto();
                    classRoomDto.setClassroomId(c.getId());
                    classRoomDto.setCollegeId(c.getCollege().getId());
                    classRoomDto.setCourseId(c.getCourse().getId());
                    classRoomDto.setGrade(c.getGrade());
                    classRoomDto.setTribeId(c.getTribe().getId());
                    return classRoomDto;
                })
                .collect(Collectors.toList());

        return new ResponseDto<>("Classrooms retrieved", 200, classRoomDtos);
    }

    @Override
    public ResponseDto<ClassRoomStats> getClassroomStats(Integer classRoomId) {
        studentService.getStudentStatsRecord("52054963");
        Optional<Classroom> classroomQuery = classroomRepository.findById(classRoomId);
        if(!classroomQuery.isPresent())
            return new ResponseDto<>("The classroom couldn't be found", 404, null);

        Classroom classroom = classroomQuery.get();
        List<Students> students = studentsRepository.findStudentsByCollegeAndGrade(classroom.getCollege().getId(),
                classroom.getGrade(), classroom.getCourse().getId());



        return null;
    }
}
