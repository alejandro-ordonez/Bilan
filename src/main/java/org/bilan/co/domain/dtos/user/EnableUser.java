package org.bilan.co.domain.dtos.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.dtos.BasicInfo;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnableUser extends BasicInfo {
    private Boolean enabled;
}
