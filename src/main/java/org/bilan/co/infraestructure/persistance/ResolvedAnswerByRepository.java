package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolvedAnswerByRepository extends JpaRepository<ResolvedAnswerBy, Integer> {

    @Query(value = "SELECT COUNT(*) FROM " +
            "( " +
            "SELECT id, COUNT(CASE WHEN times >= 3 THEN 1 END) AS checked FROM " +
            "   (" +
            "       SELECT r.id, COUNT(*) AS times FROM resolved_answer_by r " +
            "       LEFT JOIN answers a ON r.id_answer=a.id" +
            "       WHERE a.is_correct=TRUE AND r.student_id_document=:document" +
            "   ) AS results" +
            ") AS results2", nativeQuery = true)
    Integer getQuestionsCompleted(String document);
}
