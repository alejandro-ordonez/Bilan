package org.bilan.co.infraestructure.persistance;

import java.util.List;

import org.bilan.co.domain.entities.Tribes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TribesRepository extends JpaRepository<Tribes, Integer> {
}
