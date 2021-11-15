package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeachersRepository extends JpaRepository<Teachers, String> {
}
