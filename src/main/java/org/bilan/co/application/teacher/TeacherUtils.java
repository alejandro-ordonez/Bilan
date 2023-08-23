package org.bilan.co.application.teacher;

import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.teacher.TeacherDto;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.UserType;

import java.util.List;
import java.util.stream.Collectors;

public final class TeacherUtils {
    private TeacherUtils(){}

    public static TeacherDto parseTeacher(Teachers teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setCodDane(teacher.getCodDane());
        teacherDto.setClassRoomDtoList(
                teacher.getClassrooms()
                        .stream()
                        .map(TeacherUtils::parseClassRoom)
                        .collect(Collectors.toList()));

        teacherDto.setCodDaneSede(teacher.getCodDaneSede());
        teacherDto.setCodDaneMinResidencia(teacher.getCodDaneMinResidencia());
        teacherDto.setDocument(teacher.getDocument());
        teacherDto.setDocumentType(teacher.getDocumentType());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setName(teacher.getName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setIsEnabled(teacher.getIsEnabled());
        teacherDto.setUserType(UserType.Teacher);

        addTeacherExtraProperties(teacherDto, teacher.getClassrooms());
        return teacherDto;
    }

    public static void addTeacherExtraProperties(UserInfoDto userInfoDto, List<Classroom> classrooms) {
        if (classrooms == null)
            return;

        StringBuilder grades = new StringBuilder();
        StringBuilder courses = new StringBuilder();
        StringBuilder subjects = new StringBuilder();

        for (int i = 0; i < classrooms.size(); i++) {
            grades.append(classrooms.get(i).getGrade());
            courses.append(classrooms.get(i).getCourse().getName());
            subjects.append(classrooms.get(i).getTribe().getName());

            if (i != classrooms.size() - 1) {
                grades.append(", ");
                courses.append(", ");
                subjects.append(", ");
            }
        }


        userInfoDto.getMetadata().put("grades", grades.toString());
        userInfoDto.getMetadata().put("courses", courses.toString());
        userInfoDto.getMetadata().put("subjects", subjects.toString());
    }


    public static ClassRoomDto parseClassRoom(Classroom c) {
        ClassRoomDto classRoomDto = new ClassRoomDto();
        classRoomDto.setClassroomId(c.getId());
        classRoomDto.setCollegeId(c.getCollege().getId());
        classRoomDto.setCourseId(c.getCourse().getId());
        classRoomDto.setGrade(c.getGrade());
        classRoomDto.setTribeId(c.getTribe().getId());
        classRoomDto.setCollegeName(c.getCollege().getName());
        return classRoomDto;
    }

    public static Classroom classroomDtoToEntity(ClassRoomDto cr, Teachers teacher) {
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
    }
}
