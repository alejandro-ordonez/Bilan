package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Evidences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidences, Integer> {

    @Query("SELECT COUNT(e) FROM Evidences e WHERE e.idStudent.document=:document AND COUNT(e.evaluations) > 1")
    Integer findUploadedAndEvaluated(String document);

    @Query("SELECT e FROM Evidences e " +
            "WHERE e.idStudent.document=:document AND " +
            "COUNT(e.evaluations)>1")
    List<Evidences> getEvidencesEvaluated(String document);
}
