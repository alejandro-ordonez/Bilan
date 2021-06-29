package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Integer> {
}
