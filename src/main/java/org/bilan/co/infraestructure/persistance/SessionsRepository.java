package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.ActionsPoints;
import org.bilan.co.domain.entities.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionsRepository extends JpaRepository<Sessions, Integer> {

    @Query("SELECT new org.bilan.co.domain.dtos.ActionsPoints(session.actions.id, SUM(session.score), session.tribeId.id) " +
            "FROM Sessions session WHERE session.students.document = ?1 " +
            "GROUP BY session.actions")
    List<ActionsPoints> getActionsPoints(String document);

}
