package org.bilan.co.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnableUser extends BasicInfo{
    private Boolean enabled;
}
