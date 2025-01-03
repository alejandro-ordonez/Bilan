package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Challenges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengesRepository extends JpaRepository<Challenges, Integer> {
}