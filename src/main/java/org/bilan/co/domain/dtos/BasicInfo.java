package org.bilan.co.domain.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.DocumentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfo {
    @Pattern(regexp = "[1-9][0-9]{5,12}")
    protected String document;

    @NotNull
    protected DocumentType documentType;
}
