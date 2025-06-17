package org.bilan.co.infraestructure.persistance;

import jakarta.transaction.Transactional;
import org.bilan.co.domain.entities.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Transactional
    @Modifying
    @Query("UPDATE UserInfo u SET u.isEnabled = :enabled " +
            "WHERE u.document = :document")
    void updateState(String document, Boolean enabled);

    @Query("SELECT u FROM UserInfo u " +
            "WHERE NOT u.document = :document AND u.role.id IN (:roles)")
    Page<UserInfo> getUsers(Pageable page, String document,  @Param("roles") List<Integer> roles);

    @Query(value =  "SELECT u FROM UserInfo u " +
            "WHERE u.document LIKE CONCAT('%', :partialDocument, '%') " +
            "AND NOT u.document = :document AND u.role.id IN (:roles)")
    Page<UserInfo> searchUsersWithDocument(Pageable page, String partialDocument, String document, List<Integer> roles);

    @Query("UPDATE UserInfo u " +
            "SET u.isEnabled = false " +
            "WHERE u.role.id <> 5")
    void disableAllUsers();
}
