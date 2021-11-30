/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentsRepository extends JpaRepository<Students, String> {

    @Query(value = "SELECT student.grade FROM Students student WHERE student.document = ?1")
    String getGrade(String document);

    @Query(value = "" +
            "    SELECT student " +
            "      FROM Students student " +
            "JOIN FETCH student.colleges " +
            "JOIN FETCH student.courses " +
            "     WHERE student.colleges.id = ?1" +
            "  GROUP BY student.courses.id,  student.grade ")
    List<Students> getStudentsByCollege(Integer collegeId);

    @Query("SELECT student FROM Students student " +
            "WHERE student.colleges.id=:collegeId AND " +
            "student.grade=:grade AND " +
            "student.courses.id=:courseId")
    List<Students> findStudentsByCollegeAndGrade(Integer collegeId, String grade, Integer courseId);
}