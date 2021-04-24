package org.bilan.co.data;

import org.bilan.co.domain.entities.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeachersRepository extends JpaRepository<Teachers, Integer> {

    @Query(value="SELECT t FROM Teachers t WHERE t.document = ?1")
    Teachers findTeacherByDocument(String teacher);
}
