package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
}
