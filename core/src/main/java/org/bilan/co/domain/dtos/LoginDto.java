package org.bilan.co.domain.dtos;

import lombok.Data;
import org.bilan.co.domain.dtos.enums.DocumentType;
import org.bilan.co.domain.dtos.enums.UserType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LoginDto {

    @Pattern(regexp = "/[1-9][0-9]{8,12}/g")
    private String document;
    @NotNull
    private DocumentType documentType;
    @NotNull
    private UserType userType;
    private String password;

    public LoginDto(String document, DocumentType documentType, UserType userType, String password) {
        this.document = document;
        this.documentType = documentType;
        this.userType = userType;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
