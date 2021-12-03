package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Evaluation;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "CALL p_statistics_secretary()", nativeQuery = true)
    List<IPerformanceActivity> statistics();

    @Query(value = "CALL p_statistics_performance_secretary()", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance();

    @Query(value = "CALL p_statistics_secretary_state(:state)", nativeQuery = true)
    List<IPerformanceActivity> statistics(@Param("state") String state);

    @Query(value = "CALL p_statistics_performance_secretary_state(:state)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("state") String state);

    @Query(value = "CALL p_statistics_secretary_state_mun(:munId)", nativeQuery = true)
    List<IPerformanceActivity> statistics(@Param("munId") Integer munId);

    @Query(value = "CALL p_statistics_performance_secretary_state_mun(:munId)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("munId") Integer munId);

    @Query(value = "CALL p_statistics_colleges(:collegeId)", nativeQuery = true)
    List<IPerformanceActivity> statisticsCollege(@Param("collegeId") Integer collegeId);

    @Query(value = "CALL p_statistics_performance_colleges(:collegeId)", nativeQuery = true)
    List<IPerformanceGame> statisticsCollegePerformance(@Param("collegeId") Integer collegeId);

    @Query(value = "CALL p_statistics_grade(:collegeId, :grade, :courseId)", nativeQuery = true)
    List<IPerformanceActivity> statistics(@Param("collegeId") Integer collegeId, @Param("grade") String grade, @Param("courseId") Integer courseId);

    @Query(value = "CALL p_statistics_performance_grade(:collegeId, :grade, :courseId)", nativeQuery = true)
    List<IPerformanceGame> statisticsPerformance(@Param("collegeId") Integer collegeId, @Param("grade") String grade, @Param("courseId") Integer courseId);

    @Query(value = "CALL p_statistics_student(:student)", nativeQuery = true)
    List<IPerformanceActivity> statisticsStudent(@Param("student") String student);

    @Query(value = "CALL p_statistics_performance_student(:student)", nativeQuery = true)
    List<IPerformanceGame> statisticsStudentPerformance(@Param("student") String student);
}