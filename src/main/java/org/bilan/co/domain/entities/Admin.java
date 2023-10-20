package org.bilan.co.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.bilan.co.domain.enums.UserType;

@Table(name = "admin")
@Entity
public class Admin extends UserInfo {
    @Transient
    private final UserType userType = UserType.Admin;
}