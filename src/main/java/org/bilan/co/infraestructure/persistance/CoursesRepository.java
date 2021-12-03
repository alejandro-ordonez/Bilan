package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.dtos.college.CollegeDto;
import org.bilan.co.domain.entities.Courses;
import org.bilan.co.domain.projections.ICourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {


    @Query("SELECT new org.bilan.co.domain.dtos.college.CollegeDto(c.id, c.name, c.campusName, c.campusCodeDane) " +
            " FROM Colleges c " +
            "WHERE c.stateMunicipality.id = ?1")
    List<CollegeDto> getColleges(Integer stateMunId);

    @Query(value = "" +
            "SELECT DISTINCT s.course_id AS id " +
            "     , c.name AS name" +
            "     , s.cod_grade AS grade" +
            "  FROM students s " +
            "  JOIN courses c " +
            "    ON s.course_id = c.id " +
            " WHERE s.college_id = ?1 " +
            "   AND cod_grade IS NOT NULL ", nativeQuery = true)
    List<ICourse> getCoursesAndGradeWithStudentsByCollege(Integer collegeId);

    @Query("SELECT course FROM Courses course WHERE course.name = name")
    Optional<Courses> findByCourseName(String name);
}