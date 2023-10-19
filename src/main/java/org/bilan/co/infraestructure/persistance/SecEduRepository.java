package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.SecEduUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SecEduRepository extends JpaRepository<SecEduUser, Integer> {

    @Query("SELECT secEdu.state FROM SecEduUser secEdu WHERE secEdu.document = :document")
    Optional<String> getStateFromUser(String document);
}
