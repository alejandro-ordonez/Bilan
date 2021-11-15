package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.MinUser;
import org.springframework.stereotype.Repository;

@Repository
public interface MinUserRepository extends UserInfoRepository<MinUser> {
}