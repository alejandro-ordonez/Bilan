package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Contexts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContextRepository extends JpaRepository<Contexts, Integer> {

    @Query("SELECT context From Contexts context WHERE context.id IN ?1")
    List<Contexts> getContextsByIds(List<Integer> ids);
}
