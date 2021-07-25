package org.bilan.co.domain.dtos;

import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

public class AuthDto extends AuthenticatedUserDto{

    private String password;

    public AuthDto(String document, DocumentType documentType, UserType userType, String password) {
        super(document, userType, documentType);
        this.password = password;
    }

    public AuthDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
