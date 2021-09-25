package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionsRepository extends JpaRepository<Actions, Integer> {

    @Query("SELECT action FROM Actions action WHERE action.tribe = ?1")
    List<Actions> findAllByTribe(Integer tribeId);
}
