package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.StateMunicipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateMunicipalityRepository extends JpaRepository<StateMunicipality, Integer> {

    @Query("SELECT DISTINCT c.state" +
            " FROM StateMunicipality c " +
            "ORDER BY c.state")
    List<String> states();
}