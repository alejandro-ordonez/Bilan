package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
}