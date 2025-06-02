package org.bilan.co.domain.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.DocumentType;

@NoArgsConstructor
@Data
public abstract class ImportIdentifier {
    @NotNull
    protected DocumentType documentType;
    @NotEmpty(message = "El documento no puede estar vacío")
    @Pattern(regexp = "[1-9][0-9]{5,12}")
    String document;
}
