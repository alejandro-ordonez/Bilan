package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface TeachersRepository extends JpaRepository<Teachers, String> {

    @Query(value = "SELECT ui.*, t.*" +
            "  FROM teachers t" +
            "  JOIN user_info ui " +
            "    ON t.document = ui.document "+
            "  JOIN classrooms c" +
            "    ON t.document = c.teacher_id" +
            "  JOIN students s" +
            "    ON s.course_id = c.course_id AND s.cod_grade = c.grade " +
            "  JOIN evidences e" +
            "    ON e.id_student  = s.document" +
            " WHERE e.id = ?1" +
            "   AND t.document = ?2" +
            "   AND s.document = ?3" +
            "   AND NOT EXISTS (SELECT e2.id FROM evaluation e2 WHERE e2.evidences_id = e.id)", nativeQuery = true)
    Optional<Teachers> findTeacherByStudentAndEvidence(Long evidenceId, String teacherId, String studentId);
}
