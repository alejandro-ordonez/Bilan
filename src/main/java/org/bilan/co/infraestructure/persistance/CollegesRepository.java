package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.projections.ICollege;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static org.bilan.co.utils.Constants.*;

import java.util.List;

@Repository
public interface CollegesRepository extends JpaRepository<Colleges, Integer> {

    @Query("SELECT new org.bilan.co.domain.dtos.college.CollegeDto(c.id, c.name, c.campusName, c.campusCodeDane) " +
            " FROM Colleges c " +
            "WHERE c.stateMunicipality.id = ?1")
    List<CollegeDto> getColleges(Integer stateMunId);

    @Query("SELECT c " +
            " FROM Colleges c " +
            "WHERE c.campusCodeDane = ?1")
    Colleges collegeByCampusCodeDane(String campusCodeDane);

    @Query(value = "" +
            "  SELECT DISTINCT" +
            "         c.codigo_dane AS codeDane" +
            "       , c.nombre_establecimiento AS name " +
            "       , dm.departamento AS state" +
            "    FROM colleges c " +
            "    JOIN departamento_municipio dm " +
            "      ON dm.id = c.dep_mun_id " +
            "   WHERE dm.departamento = :state " +
            "GROUP BY c.codigo_dane " +
            "ORDER BY c.id ", nativeQuery = true)
    @Cacheable(CACHE_COLLEGES_BY_STATE)
    List<ICollege> findByState(@Param("state") String state);
}