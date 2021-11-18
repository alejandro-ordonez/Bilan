package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Evidences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidences, Integer> {
}
