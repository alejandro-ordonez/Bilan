package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResolvedAnswerByRepository extends JpaRepository<ResolvedAnswerBy, Integer> {
/*
    @Query("SELECT answer FROM ResolvedAnswerBy answer " +
            "JOIN answer.idQuestion question" +
            "WHERE answer.idStudent = ?1 AND question.idTribe = ?2 AND answer.idAction = ?3")
    List<ResolvedAnswerBy> getRecords(String studentId, Integer tribeId, Integer actionId);*/

    @Query("SELECT SUM(answer.idChallenge.reward) FROM ResolvedAnswerBy answer " +
            "JOIN answer.idQuestion question " +
            "JOIN answer.idChallenge challenge "+
            "WHERE (answer.idStudent = ?1 AND question.idTribe = ?2 AND challenge.action = ?3)")
    Integer getScore(String studentId, Integer tribeId, Integer actionId);

}
