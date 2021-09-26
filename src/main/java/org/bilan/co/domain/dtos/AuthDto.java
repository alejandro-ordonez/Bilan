package org.bilan.co.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

@Data
@NoArgsConstructor
public class AuthDto extends AuthenticatedUserDto {

    private String password;
    private String grade;

    public AuthDto(String document, DocumentType documentType, UserType userType, String password) {
        super(document, userType, documentType);
        this.password = password;
    }
}
