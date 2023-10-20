package org.bilan.co.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.bilan.co.domain.enums.UserType;

@Entity
public class MinUser extends UserInfo {
    @Transient
    private final UserType userType = UserType.MinUser;
}