package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Questions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    @Query("SELECT question FROM Questions question " +
            "WHERE question.grade = :grade AND " +
            "question.idTribe.id = :tribeId " +
            "GROUP BY question.contexts.id")
    List<List<Questions>> getQuestions(Pageable pageRequest, String grade, Integer tribeId);
}
