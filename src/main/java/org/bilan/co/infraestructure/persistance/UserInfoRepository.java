package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Transactional
    @Modifying
    @Query("UPDATE UserInfo u SET u.isEnabled = :enabled " +
            "WHERE u.document = :document")
    void updateState(String document, Boolean enabled);

    @Query("SELECT u FROM UserInfo u " +
            "WHERE (u.role.id = 4 OR u.role.id = 5 OR u.role.id = 6) AND " +
            "NOT u.document = :document")
    Page<UserInfo> getUsersAdmin(Pageable page, String document);

    @Query(value =  "SELECT u FROM UserInfo u " +
            "WHERE (u.role.id = 4 OR u.role.id = 5 OR u.role.id = 6) AND " +
            "u.document LIKE CONCAT('%', :partialDocument, '%') " +
            "AND NOT u.document = :document")
    Page<UserInfo> searchUsersWithDocument(Pageable page, String partialDocument, String document);


}
