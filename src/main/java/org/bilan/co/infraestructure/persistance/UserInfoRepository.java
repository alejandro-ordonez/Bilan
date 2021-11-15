package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository<T extends UserInfo> extends JpaRepository<T, String> {

    @Query("UPDATE UserInfo u SET u.isEnabled = :enabled " +
            "WHERE u.document = :document")
    boolean updateState(String document, Boolean enabled);
}
