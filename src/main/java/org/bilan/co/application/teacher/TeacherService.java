package org.bilan.co.application.teacher;

import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.student.StudentService;
import org.bilan.co.application.user.UserService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.college.ClassRoomStats;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.student.StudentStatsRecord;
import org.bilan.co.domain.dtos.teacher.TeacherDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnrollmentDto;
import org.bilan.co.domain.entities.Classroom;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.infraestructure.persistance.ClassroomRepository;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private CollegesRepository collegesRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
                .stream().map(cr -> TeacherUtils.classroomDtoToEntity(cr, teacher)).collect(Collectors.toList());

        teacher.getClassrooms().addAll(classroomToEnroll);
        teachersRepository.save(teacher);

        return new ResponseDto<>("Teacher enrolled to courses", 200, "Ok");
    }

    @Override
    public ResponseDto<List<ClassRoomDto>> getClassrooms(String jwt) {

        AuthenticatedUserDto user = jwtUtils.getInfoFromToken(jwt);
        List<ClassRoomDto> classRoomDtos;

        switch (user.getUserType()) {
            case Admin:
                classRoomDtos = classroomRepository.findAll()
                        .stream()
                        .map(TeacherUtils::parseClassRoom)
                        .collect(Collectors.toList());
                break;

            case Teacher:
                Optional<Teachers> teachers = teachersRepository.findById(user.getDocument());
                classRoomDtos = teachers.map(value -> value.getClassrooms()
                                .stream()
                                .map(TeacherUtils::parseClassRoom)
                                .collect(Collectors.toList()))
                        .orElseGet(ArrayList::new);
                break;

            default:
                classRoomDtos = new ArrayList<>();
                break;
        }

        return new ResponseDto<>("Classrooms retrieved", 200, classRoomDtos);
    }

    @Override
    public ResponseDto<ClassRoomStats> getClassroomStats(Integer classRoomId) {

        Optional<Classroom> classroomQuery = classroomRepository.findById(classRoomId);
        if(!classroomQuery.isPresent())
            return new ResponseDto<>("The classroom couldn't be found", 404, null);

        Classroom classroom = classroomQuery.get();
        // TOOD: Change
        List<Students> students = studentsRepository.findStudentsByCollegeAndGrade(classroom.getCollege().getId(),
                classroom.getGrade(), classroom.getCourse().getId());

        List<StudentStatsRecord> studentStatsRecords = new ArrayList<>();

        students.forEach(s -> studentStatsRecords.add(studentService.getStudentStatsRecord(s.getDocument())));

        ClassRoomStats classRoomStats = new ClassRoomStats();
        classRoomStats.setStudentStatsRecords(studentStatsRecords);
        classRoomStats.setStudents(students.size());

        return new ResponseDto<>("Classroom stats retrieved", 200, classRoomStats);
    }

    @Override
    public ResponseDto<TeacherDto> getTeacher(String document) {
        Optional<Teachers> teacherQuery = this.teachersRepository.findById(document);
        if(!teacherQuery.isPresent()){
            return new ResponseDto<>("User not found", 404, null);
        }

        TeacherDto teacher = TeacherUtils.parseTeacher(teacherQuery.get());
        return  new ResponseDto<>("Teacher found", 200, teacher);
    }

    @Override
    public ResponseDto<String> updateTeacher(TeacherDto teacherDto) {
        Optional<Teachers> teacher = this.teachersRepository.findById(teacherDto.getDocument());

        if (teacher.isEmpty()) {
            return new ResponseDto<>("Failed to update the teacher, it doesn't exists", 400, "Error");
        }

        Teachers teacherUpdated = teacher.get();
        UserService.updateUserEntityFromDto(teacherUpdated, teacherDto);

        Optional<Colleges> college = collegesRepository.findByCodDaneSede(teacherDto.getCodDaneSede());

        college.ifPresent(teacherUpdated::setCollege);

        teacherUpdated.setEmail(teacherDto.getEmail());

        List<Classroom> classrooms = teacherDto.getClassRoomDtoList()
                .stream()
                .map(classRoomDto -> TeacherUtils.classroomDtoToEntity(classRoomDto, teacherUpdated))
                .collect(Collectors.toList());

        teacherUpdated.setClassrooms(classrooms);

        return new ResponseDto<>("Teacher updated", 200, "Success");
    }

    @Override
    public ResponseDto<PagedResponse<TeacherDto>> getTeachers(Integer nPage, String partialDocument, String jwt) {
        AuthenticatedUserDto authenticatedUserDto = jwtTokenUtil.getInfoFromToken(jwt);
        Optional<Teachers> teacher = teachersRepository.findById(authenticatedUserDto.getDocument());

        if(!teacher.isPresent()){
            return new ResponseDto<>("User not found", 404, null);
        }

        Page<Teachers> query;
        if(partialDocument == null)
            partialDocument = "";

        String purgedDocument = partialDocument.trim();

        if(purgedDocument.isEmpty())
            query = teachersRepository.getTeachersFromCodDaneSede(PageRequest.of(nPage, 10),
                    authenticatedUserDto.getDocument(), teacher.get().getCollege().getCampusCodeDane());

        else
            query = teachersRepository.searchTeacherWithDocument(
                    PageRequest.of(nPage, 10), authenticatedUserDto.getDocument(), partialDocument, teacher.get().getCollege().getCampusCodeDane());

        PagedResponse<TeacherDto> teacherResponse = new PagedResponse<>();
        teacherResponse.setNPages(query.getTotalPages());

        List<TeacherDto> teachers = query.getContent()
                .stream()
                .map(TeacherUtils::parseTeacher)
                .collect(Collectors.toList());

        teacherResponse.setData(teachers);

        return new ResponseDto<>("Teachers returned successfully", 200, teacherResponse);
    }
}
