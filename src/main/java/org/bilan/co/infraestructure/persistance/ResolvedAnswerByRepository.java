package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolvedAnswerByRepository extends JpaRepository<ResolvedAnswerBy, Integer> {

    @Query(value = "SELECT COUNT(*) FROM (SELECT q.id, SUM(a.is_correct) AS times_correct FROM questions q \n" +
            "LEFT JOIN resolved_answer_by r ON q.id = r.id_question \n" +
            "LEFT JOIN answers a ON r.id_answer = a.id \n" +
            "WHERE r.student_id_document = '123456789'\n" +
            "GROUP BY q.id) AS questions_completed WHERE times_correct >=3", nativeQuery = true)
    Integer getQuestionsCompleted(String document);
}
