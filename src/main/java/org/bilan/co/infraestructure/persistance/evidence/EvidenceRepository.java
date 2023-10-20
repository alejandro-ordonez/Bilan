package org.bilan.co.infraestructure.persistance.evidence;

import org.bilan.co.domain.entities.Evidences;
import org.bilan.co.domain.projections.IEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidences, Long> {

    @Query(value = "SELECT s.document AS document" +
            "     , ui.name AS name" +
            "     , ui.last_name AS lastName" +
            "     , e.created_at AS uploadedDate" +
            "     , e.file_name as fileNameEvidence " +
            "     , CASE WHEN ev.id IS NULL THEN false ELSE true END AS hasEvaluation" +
            "  FROM evidences e   " +
            "  JOIN students s" +
            "    ON e.id_student = s.document " +
            "  JOIN user_info ui " +
            "    ON s.document = ui.document " +
            "  JOIN classrooms c " +
            "    ON s.course_id = c.course_id AND s.cod_grade = c.grade AND e.id_tribe = c.tribe_id  " +
            "  JOIN teachers t " +
            "    ON t.document = c.teacher_id " +
            " LEFT JOIN evaluation ev " +
            "    ON ev.evidences_id = e.id " +
            " WHERE t.document = :teacherId" +
            "   AND e.id_tribe = :tribeId " +
            "   AND e.phase = :phase " +
            "   AND c.course_id = :courseId" +
            "   AND s.cod_grade = :grade ", nativeQuery = true)
    Optional<List<IEvidence>> filter(@Param("grade") String grade,
                                     @Param("tribeId") Integer tribeId,
                                     @Param("courseId") Integer courseId,
                                     @Param("phase") String phase,
                                     @Param("teacherId") String teacherId);

    @Query("SELECT COUNT(e) FROM Evidences e " +
            "WHERE e.idStudent.document=:document AND " +
            "e.evaluations.size()>=1")
    Integer findUploadedAndEvaluated(String document);

    @Query("SELECT e FROM Evidences e " +
            "WHERE e.idStudent.document=:document AND " +
            "e.evaluations.size()>=1")
    List<Evidences> getEvidencesEvaluated(String document);

    Optional<Evidences> findByFileName(String fileName);
}
