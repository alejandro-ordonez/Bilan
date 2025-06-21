package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.GameCycles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.Optional;

public interface GameCycleRepository extends JpaRepository<GameCycles, String> {

    @Query("SELECT cycle FROM GameCycles cycle " +
            "WHERE cycle.gameStatus = GameCycleStatus.Active OR cycle.gameStatus = GameCycleStatus.ProcessingClosing")
    Optional<GameCycles> getActiveCycle();

    @Query("SELECT cycle FROM GameCycles cycle " +
            "ORDER BY cycle.startDate")
    Page<GameCycles> getCycles(Pageable page);

    @Procedure(procedureName = "p_clean_student_data")
    void cleanStudentData();
}
