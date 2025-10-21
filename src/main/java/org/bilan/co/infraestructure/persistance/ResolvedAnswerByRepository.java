package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolvedAnswerByRepository extends JpaRepository<ResolvedAnswerBy, Integer> {

    @Query(value = """
            SELECT COUNT(*) FROM (
              SELECT r.id_question, SUM(a.is_correct) AS times_correct
              FROM resolved_answer_by r
              JOIN answers a ON r.id_answer = a.id
              WHERE r.student_id_document = :document
              GROUP BY r.id_question
              HAVING times_correct >= 3
            ) AS questions_completed""", nativeQuery = true)
    Integer getQuestionsCompleted(@Param("document") String document);
}
