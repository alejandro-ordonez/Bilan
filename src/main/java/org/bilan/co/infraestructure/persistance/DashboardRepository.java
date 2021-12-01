package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.college.IPerformanceActivities;
import org.bilan.co.domain.dtos.college.IPerformanceGame;
import org.bilan.co.domain.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "CALL p_statistics_secretary()", nativeQuery = true)
    List<IPerformanceActivities> statistics();

    @Query(value = "CALL p_statistics_secretary_mun(:municipality)", nativeQuery = true)
    List<IPerformanceActivities> statistics(@Param("municipality") String municipality);

    @Query(value = "CALL p_statistics_colleges(:college)", nativeQuery = true)
    List<IPerformanceActivities> statistics(@Param("college") Integer college);

    @Query(value = "CALL p_statistics_grade(:college, :grade, :course)", nativeQuery = true)
    List<IPerformanceActivities> statistics(@Param("college") Integer college, @Param("grade") String grade, @Param("course") Integer course);

    @Query(value = "CALL p_statistics_student(:college, :student)", nativeQuery = true)
    List<IPerformanceActivities> statistics(@Param("college") Integer college, @Param("student") String student);

    @Query(value = "CALL p_statistics_performance_secretary()", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance();

    @Query(value = "CALL p_statistics_performance_secretary_mun(:municipality)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("municipality") String municipality);

    @Query(value = "CALL p_statistics_performance_colleges(:college)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("college") Integer college);

    @Query(value = "CALL p_statistics_performance_grade(:college, :grade, :course)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("college") Integer college, @Param("grade") String grade, @Param("course") Integer course);

    @Query(value = "CALL p_statistics_performance_student(:college, :student)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("college") Integer college, @Param("student") String student);
}