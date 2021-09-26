package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Integer> {

    @Query("SELECT answer FROM Answers answer " +
            "WHERE answer.idQuestion.id = :questionId " +
            "AND answer.isCorrect = true")
    List<Answers> getAnswersByQuestion(int questionId);

}
