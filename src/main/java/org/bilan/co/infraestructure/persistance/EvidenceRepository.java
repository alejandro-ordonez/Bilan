package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Evidences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidences, Integer> {

    @Query(value = "SELECT evidence.id, COUNT(CASE WHEN teacher_document IS NOT NULL THEN 1 END) AS evaluations FROM evidences evidence \n" +
            "LEFT JOIN evaluation eval ON eval.evidences_id=evidence.id \n" +
            "WHERE evidence.id_student=:document\n" +
            "GROUP BY evidences_id ", nativeQuery = true)
    Integer findUploadedAndEvaluated(String document);

    @Query("SELECT e FROM Evidences e " +
            "WHERE e.idStudent.document=:document AND " +
            "e.evaluations.size>=1")
    List<Evidences> getEvidencesEvaluated(String document);
}
