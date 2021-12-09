package org.bilan.co.domain.entities;

import org.bilan.co.domain.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "admin")
@Entity
public class Admin extends UserInfo {
    @Transient
    private UserType userType = UserType.Admin;
}