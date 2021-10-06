package org.bilan.co.domain.dtos;

import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AuthenticatedUserDto {
    @Pattern(regexp = "[1-9][0-9]{6,12}")
    private String document;
    @NotNull
    private DocumentType documentType;
    @NotNull
    private UserType userType;

    public AuthenticatedUserDto(String document,  UserType userType, DocumentType documentType) {
        this.document = document;
        this.documentType = documentType;
        this.userType = userType;
    }

    public AuthenticatedUserDto(){
        this.document = "";
        this.documentType= DocumentType.Unknown;
        this.userType = UserType.Unknown;
    }


    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
