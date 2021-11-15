package org.bilan.co.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfo {

    @Pattern(regexp = "[1-9][0-9]{6,12}")
    protected String document;

    @NotNull
    protected UserType userType;
}
