/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.bilanbackend.data;

import org.bilan.co.bilanbackend.domain.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Manuel Alejandro
 */
@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer>{
    /**
     * Queries an Student that has the given docuement
     * @param document Document associated to the Student
     * @return 
     */
    @Query(value="SELECT s FROM Students s WHERE s.document = ?1")
    public Students findByNumber(int document);
}
