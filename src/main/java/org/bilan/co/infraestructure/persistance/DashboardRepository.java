package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.college.IModuleDashboard;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "CALL p_statistics_secretary()", nativeQuery = true)
    List<IModuleDashboard> statistics();

    @Query(value = "CALL p_statistics_secretary_mun(:municipality)", nativeQuery = true)
    List<IModuleDashboard> statistics(@Param("municipality") String municipality);

    @Query(value = "CALL p_statistics_colleges(:college)", nativeQuery = true)
    List<IModuleDashboard> statistics(@Param("college") Integer college);

    @Query(value = "CALL p_statistics_grade(:college, :grade, :course)", nativeQuery = true)
    List<IModuleDashboard> statistics(@Param("college") Integer college, @Param("grade") String grade, @Param("course") Integer course);

    @Query(value = "CALL p_statistics_student(:college, :student)", nativeQuery = true)
    List<IModuleDashboard> statistics(@Param("college") Integer college, @Param("student") String student);
}