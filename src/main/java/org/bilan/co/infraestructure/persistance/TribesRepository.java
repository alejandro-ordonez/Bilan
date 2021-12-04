package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.TribeDto;
import org.bilan.co.domain.entities.Tribes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TribesRepository extends JpaRepository<Tribes, Integer> {

    @Query("SELECT new org.bilan.co.domain.dtos.TribeDto(t.id, t.name, t.culture, t.element, t.adjacentTribeId.id, t.oppositeTribeId.id) " +
            "FROM Tribes t ")
    List<TribeDto> getAllDtos();

    @Query("SELECT t FROM Tribes t WHERE t.name = :tribeName")
    Optional<Tribes> getByName(String tribeName);
}
