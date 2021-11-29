package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.dtos.college.IModuleDashboard;
import org.bilan.co.domain.entities.Colleges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegesRepository extends JpaRepository<Colleges, String> {

    @Query("SELECT new org.bilan.co.domain.dtos.college.CollegeDto(c.id, c.name, c.campusName, c.campusCodeDane) " +
            " FROM Colleges c " +
            "WHERE c.stateMunicipality.id = ?1")
    List<CollegeDto> getColleges(Integer stateMunId);

    @Query("SELECT c " +
            " FROM Colleges c " +
            "WHERE c.campusCodeDane = ?1")
    Colleges collegeByCampusCodeDane(String campusCodeDane);

    @Query(value = "" +
            "SELECT pa.id" +
            "     , pa.name" +
            "     , pa.score " +
            "     , pg.points " +
            "  FROM v_performance_activities pa " +
            "  JOIN v_performance_game pg " +
            "    ON pa.id = pg.id " +
            " WHERE pa.college = :college", nativeQuery = true)
    List<IModuleDashboard> statistics(@Param("college") Integer college);
}