package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.StudentStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<StudentStats, Integer> {

    @Query(value="SELECT stats FROM StudentStats stats " +
            "INNER JOIN stats.idStudent student WHERE student.document = ?1")
    StudentStats findByDocument(String document);


    @Query(value = "SELECT s.time_in_game FROM student_stats s WHERE s.id_student = :document", nativeQuery = true)
    Float getTimeInApp(String document);
}
