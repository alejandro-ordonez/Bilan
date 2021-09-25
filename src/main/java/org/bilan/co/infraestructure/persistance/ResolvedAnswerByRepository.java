package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResolvedAnswerByRepository extends JpaRepository<ResolvedAnswerBy, Integer> {

}
