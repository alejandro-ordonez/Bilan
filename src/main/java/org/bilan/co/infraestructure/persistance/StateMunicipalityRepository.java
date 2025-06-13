package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.StateMunicipality;
import org.bilan.co.domain.projections.IMunicipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateMunicipalityRepository extends JpaRepository<StateMunicipality, Integer> {

    @Query("SELECT DISTINCT c.state " +
            " FROM StateMunicipality c " +
            "ORDER BY c.state")
    List<String> states();

    @Query("SELECT mun FROM StateMunicipality mun " +
            "WHERE mun.codDaneMunicipality = :codDane")
    Optional<StateMunicipality> findByCodDane(String codDane);

    @Query(value = "  SELECT DISTINCT dm.id   " +
            "      , dm.municipio AS name " +
            "   FROM departamento_municipio dm  " +
            "  WHERE dm.departamento = :state   " +
            "ORDER BY dm.id ", nativeQuery = true)
    List<IMunicipality> findMunicipalitiesByState(@Param("state") String state);
}