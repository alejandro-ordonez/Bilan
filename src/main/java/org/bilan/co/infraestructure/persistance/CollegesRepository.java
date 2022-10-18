package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.projections.ICollege;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.bilan.co.utils.Constants.CACHE_COLLEGES_BY_MUN;

@Repository
public interface CollegesRepository extends JpaRepository<Colleges, Integer> {

    @Query("SELECT new org.bilan.co.domain.dtos.college.CollegeDto(c.id, c.name, c.campusName, c.campusCodeDane) " +
            " FROM Colleges c " +
            "WHERE c.stateMunicipality.id = ?1")
    List<CollegeDto> getColleges(Integer stateMunId);

    @Query("SELECT c FROM Colleges c WHERE c.campusCodeDane = :codDane")
    Optional<Colleges> findByCodDaneSede(String codDane);

    @Query(value = "" +
            "  SELECT c.id AS id " +
            "       , CONCAT(c.nombre_establecimiento, ' - ', c.nombre_sede) AS name " +
            "       , COUNT(s.document) AS numberStudents" +
            "    FROM colleges c " +
            "    INNER JOIN students s  ON c.id=s.college_id " +
            "   WHERE c.id = :collegeId " +
            "GROUP BY c.id ", nativeQuery = true)
    ICollege singleById(@Param("collegeId") Integer collegeId);

    @Query(value = "" +
            "  SELECT DISTINCT" +
            "         c.id AS id " +
            "       , CONCAT(c.nombre_establecimiento, ' - ', c.nombre_sede) AS name " +
            "    FROM colleges c " +
            "    JOIN departamento_municipio dm " +
            "      ON dm.id = c.dep_mun_id " +
            "   WHERE dm.id = :munId " +
            "GROUP BY c.codigo_dane " +
            "ORDER BY c.id ", nativeQuery = true)
    @Cacheable(CACHE_COLLEGES_BY_MUN)
    List<ICollege> findByMunId(@Param("munId") Integer munId);

    @Query("SELECT c " +
            " FROM Colleges c " +
            "WHERE c.campusCodeDane = ?1")
    Colleges collegeByCampusCodeDane(String campusCodeDane);

    @Query("SELECT c.id FROM Colleges c WHERE c.campusCodeDane = :codDaneSede")
    Integer getIdByCodDaneSede(String codDaneSede);
}