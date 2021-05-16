package org.bilan.co.domain.dtos;

import lombok.Data;
import org.bilan.co.domain.dtos.enums.DocumentType;
import org.bilan.co.domain.dtos.enums.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
