package org.bilan.co.domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.BasicInfo;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserDto extends BasicInfo {
    @NotNull
    private UserType userType;

    public AuthenticatedUserDto(String document, UserType userType, DocumentType documentType) {
        this.document = document;
        this.userType = userType;
        this.documentType = documentType;
    }
}