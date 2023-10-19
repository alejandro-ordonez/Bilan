package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Data
@NoArgsConstructor
@Table(name = "sec_edu_user")
public class SecEduUser extends UserInfo {

    @Transient
    UserType userType = UserType.SecEdu;

    String state;
}
