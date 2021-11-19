package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.CollegeDto;
import org.bilan.co.domain.entities.Colleges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegesRepository extends JpaRepository<Colleges, String> {

    @Query("SELECT new org.bilan.co.domain.dtos.CollegeDto(c.id, c.name) FROM Colleges c")
    List<CollegeDto> getColleges();

}