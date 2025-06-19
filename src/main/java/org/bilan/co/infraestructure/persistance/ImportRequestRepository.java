package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.ImportRequests;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImportRequestRepository extends JpaRepository<ImportRequests, String> {

    @Query("SELECT r FROM ImportRequests r " +
            "WHERE :requestor IS NULL OR r.requestor.document = :requestor " +
            "ORDER BY r.created DESC"
    )
    Page<ImportRequests> getRequests(Pageable page, String requestor);


    @Query("SELECT r " +
            "FROM ImportRequests r " +
            "WHERE (r.status = ImportStatus.ApprovedWithErrors OR r.status = ImportStatus.Queued) OR " +
            "(r.status = ImportStatus.Processing AND TIMESTAMPDIFF(MINUTE, r.modified, CURRENT_TIMESTAMP) > 10 ) ")
    List<ImportRequests> getPendingRequests();

    @Query("SELECT r " +
            "FROM ImportRequests r " +
            "WHERE r.status = ImportStatus.ReadyForVerification OR " +
            "(r.status = ImportStatus.Verifying AND TIMESTAMPDIFF(MINUTE, r.modified, CURRENT_TIMESTAMP) > 10 ) ")
    List<ImportRequests> getReadyForVerification();
}
