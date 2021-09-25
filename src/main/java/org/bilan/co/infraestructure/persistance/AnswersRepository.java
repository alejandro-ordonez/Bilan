package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Integer> {
}
