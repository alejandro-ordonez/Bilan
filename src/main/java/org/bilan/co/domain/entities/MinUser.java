package org.bilan.co.domain.entities;

import org.bilan.co.domain.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class MinUser extends UserInfo {
    @Transient
    private UserType userType = UserType.Min;
}