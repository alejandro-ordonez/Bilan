package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Query("UPDATE UserInfo u SET u.isEnabled = :enabled " +
            "WHERE u.document = :document")
    boolean updateState(String document, Boolean enabled);

    @Query(value = "SELECT * FROM user_info u WHERE u.role_id = 4 OR u.role_id = 5 OR u.role_id = 6",
            countQuery = "SELECT COUNT(*) FROM user_info u WHERE u.role_id = 4 OR u.role_id = 5 OR u.role_id = 6",
            nativeQuery = true)
    Page<UserInfo> getUsersAdmin(Pageable page);

    @Query(value =  "SELECT * FROM user_info u WHERE (u.role_id = 4 OR u.role_id = 5 OR u.role_id = 6) AND u.document LIKE CONCAT('%', :partialDocument, '%')",
            countQuery = "SELECT COUNT(*) FROM user_info u WHERE (u.role_id = 4 OR u.role_id = 5 OR u.role_id = 6) AND u.document LIKE CONCAT('%', :partialDocument, '%')",
            nativeQuery = true)
    Page<UserInfo> searchUsersWithDocument(Pageable page, String partialDocument);


}
