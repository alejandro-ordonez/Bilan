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

    @Query(value = " " +
            "    SELECT dm.departamento  " +
            "        , COUNT(DISTINCT s.document) AS n_students " +
            "        , COUNT(s2.tribe_id) AS n_login_by_student " +
            "     FROM students s  " +
            "     JOIN colleges c  " +
            "       ON s.college_id = c.id  " +
            "     JOIN departamento_municipio dm  " +
            "       ON dm.id = c.dep_mun_id  " +
            "     JOIN user_info ui  " +
            "       ON s.document = ui.document  " +
            "LEFT JOIN sessions s2  " +
            "       ON s2.student_id = s.document " +
            "    WHERE ui.is_enabled = 1 " +
            " GROUP BY dm.departamento  ", nativeQuery = true)
    List<Object[]> login();

    @Query(value = " " +
            "   SELECT dm.id  " +
            "        , COUNT(DISTINCT s.document)" +
            "        , COUNT(s2.tribe_id)" +
            "     FROM students s  " +
            "     JOIN colleges c  " +
            "       ON s.college_id = c.id  " +
            "     JOIN departamento_municipio dm  " +
            "       ON dm.id = c.dep_mun_id  " +
            "     JOIN user_info ui  " +
            "       ON s.document = ui.document  " +
            "LEFT JOIN sessions s2  " +
            "       ON s2.student_id = s.document " +
            "    WHERE ui.is_enabled = 1 " +
            "      AND dm.departamento = :state " +
            " GROUP BY dm.id ", nativeQuery = true)
    List<Object[]> loginState(@Param("state") String state);

    @Query(value = " " +
            "   SELECT s.college_id " +
            "        , COUNT(DISTINCT s.document) " +
            "        , COUNT(s2.tribe_id) " +
            "     FROM students s " +
            "     JOIN colleges c " +
            "       ON s.college_id = c.id " +
            "     JOIN user_info ui " +
            "       ON s.document = ui.document " +
            "LEFT JOIN sessions s2 " +
            "       ON s2.student_id = s.document " +
            "    WHERE ui.is_enabled = 1 " +
            "      AND c.dep_mun_id = :munId " +
            " GROUP BY c.id ", nativeQuery = true)
    List<Object[]> loginMunicipality(@Param("munId") Integer munId);

    @Query(value = "" +
            "   SELECT s2.tribe_id  " +
            "        , COUNT(DISTINCT s.document) " +
            "        , COUNT(s2.tribe_id) " +
            "     FROM students s  " +
            "     JOIN user_info ui  " +
            "       ON s.document = ui.document  " +
            "     JOIN sessions s2  " +
            "       ON s2.student_id = s.document " +
            "    WHERE ui.is_enabled = 1 " +
            "      AND s.college_id = :collegeId " +
            " GROUP BY s2.tribe_id ", nativeQuery = true)
    List<Object[]> loginCollege(@Param("collegeId") Integer collegeId);

    @Query(value = "" +
            "   SELECT s.document  " +
            "        , COUNT(s2.id) " +
            "     FROM students s  " +
            "     JOIN user_info ui  " +
            "       ON s.document = ui.document  " +
            "     JOIN sessions s2  " +
            "       ON s2.student_id = s.document " +
            "    WHERE ui.is_enabled = 1 " +
            "      AND s.cod_grade = :grade " +
            "      AND s.course_id = :courseId " +
            "      AND s.college_id = :collegeId  " +
            " GROUP BY s.document ", nativeQuery = true)
    List<Object[]> loginCourseGrade(@Param("collegeId") Integer collegeId, @Param("grade") String grade, @Param("courseId") Integer courseId);
}