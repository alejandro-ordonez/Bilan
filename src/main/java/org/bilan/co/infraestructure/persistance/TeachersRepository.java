package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.entities.Teachers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT t.college.campusCodeDane FROM Teachers t WHERE t.document = :document")
    String getCodDaneSede(String document);


    @Query(value =  "SELECT t FROM Teachers t " +
            "WHERE t.document LIKE CONCAT('%', :partialDocument, '%') " +
            "AND NOT t.document = :document AND t.college.campusCodeDane = :codDaneSede")
    Page<Teachers> searchTeacherWithDocument(Pageable page, String document, String partialDocument, String codDaneSede);

    @Query(value =  "SELECT t FROM Teachers t " +
            "WHERE NOT t.document = :document AND t.college.campusCodeDane = :codDaneSede")
    Page<Teachers> getTeachersFromCodDaneSede(Pageable page, String document, String codDaneSede);

    @Modifying
    @Query("UPDATE Teachers t SET t.college = :college WHERE t.document = :document")
    void updateTeacherCollege(String document, Colleges college);


    @Procedure("teachers_batch_insert")
    void upsertTeachers(
            @Param("p_documents") String documents,
            @Param("p_document_types") String documentTypes,
            @Param("p_names") String names,
            @Param("p_last_names") String lastNames,
            @Param("p_emails") String emails
    );
}
