/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer>{

    @Query(value="SELECT s FROM Students s WHERE s.document = ?1")
    Students findByDocument(String document);

}