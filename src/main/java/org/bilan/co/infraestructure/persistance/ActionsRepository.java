package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionsRepository extends JpaRepository<Actions, Integer> {
}


