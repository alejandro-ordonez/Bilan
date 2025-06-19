package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    @Query("SELECT c FROM Classroom c WHERE c.teacher.document = :document")
    Optional<Classroom> findFirstByTeacher(String document);

    @Query("SELECT c FROM Classroom c WHERE c.teacher.document = :document")
    List<Classroom> getTeacherClassrooms(String document);


    @Query(value = "SELECT EXISTS(SELECT 1 FROM classrooms c " +
            "WHERE c.teacher_id = :teacherDocument AND " +
            "c.college_id = :collegeId AND " +
            "c.tribe_id = :tribeId AND " +
            "c.course_id = :courseId AND " +
            "c.grade = :grade)",
            nativeQuery = true)
    Integer classRoomExists(String teacherDocument, int collegeId, int tribeId, int courseId, String grade);
}