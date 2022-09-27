/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentsRepository extends JpaRepository<Students, String> {

    @Query(value = "SELECT student.grade FROM Students student WHERE student.document = ?1")
    String getGrade(String document);

    @Query("SELECT student " +
            " FROM Students student " +
            "WHERE student.colleges.id = :collegeId  " +
            "  AND student.grade=:grade " +
            "  AND student.courses.id=:courseId")
    List<Students> findStudentsByCollegeAndGrade(Integer collegeId, String grade, Integer courseId);


    @Query("SELECT s FROM Students s " +
            "WHERE NOT s.document = :document AND s.colleges.id = :collegeId")
    Page<Students> getStudents(Pageable page, String document, int collegeId);

    @Query(value =  "SELECT s FROM Students s " +
            "WHERE s.document LIKE CONCAT('%', :partialDocument, '%') " +
            "AND NOT s.document = :document AND s.colleges.id = :collegeId")
    Page<Students> searchStudentsWithDocument(Pageable page, String partialDocument, String document, int collegeId);
}