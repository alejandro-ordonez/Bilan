package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Questions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    @Query(value = " SELECT question.* FROM questions question\n" +
            "\tLEFT JOIN (\n" +
            "\t\tSELECT * FROM (\n" +
            "\t\t\tSELECT q.id AS qId, SUM(a.is_correct) AS times_correct FROM questions q \n" +
            "\t\t\tLEFT JOIN resolved_answer_by r ON q.id = r.id_question \n" +
            "\t\t\tLEFT JOIN answers a ON r.id_answer = a.id \n" +
            "\t\t\tWHERE r.student_id_document = :document\n" +
            "\t\t\tGROUP BY q.id) AS questions_completed \n" +
            "\t\t\tWHERE times_correct >=3\n" +
            "\t\t) AS results \n" +
            "\tON qId=question.id WHERE qId IS NULL AND question.id_tribe = :tribeId AND question.grade = :grade\n" +
            "\tORDER BY RAND() LIMIT :nQuestions\n", nativeQuery = true)
    List<Questions> getQuestions(Integer nQuestions, String document, String grade, Integer tribeId);


    @Query( value = "SELECT id FROM (SELECT q.id, SUM(a.is_correct) AS times_correct FROM questions q \n" +
            "LEFT JOIN resolved_answer_by r ON q.id = r.id_question \n" +
            "LEFT JOIN answers a ON r.id_answer = a.id \n" +
            "WHERE r.student_id_document = '123456789'\n" +
            "GROUP BY q.id) AS questions_completed WHERE times_correct >=3", nativeQuery = true)
    List<Integer> getAlreadyResolvedQuestions(String document);


    @Query("SELECT question FROM Questions question " +
            "WHERE question.grade = :grade ")
    List<Questions> getQuestions(String grade);
}
