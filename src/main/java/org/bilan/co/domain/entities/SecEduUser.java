package org.bilan.co.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.UserType;

@Entity
@Data
@NoArgsConstructor
@Table(name = "sec_edu_user")
public class SecEduUser extends UserInfo {

    @Transient
    UserType userType = UserType.SecEdu;

    String state;
}
