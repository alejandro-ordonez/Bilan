package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    @Query("SELECT c FROM Classroom c WHERE c.teacher.document = :document LIMIT 1")
    Optional<Classroom> getByTeacher(String document);
}