package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}