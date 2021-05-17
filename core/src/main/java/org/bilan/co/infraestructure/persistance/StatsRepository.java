package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.StudentStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatsRepository extends JpaRepository<StudentStats, Integer> {
    @Query(value="SELECT s FROM StudentStats s WHERE s.document = ?1")
    StudentStats findByDocument(String document);
}
