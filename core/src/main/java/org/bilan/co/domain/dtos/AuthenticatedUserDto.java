package org.bilan.co.domain.dtos;

public class AuthenticatedUserDto {
    private String document;
    private String userType;
    private String documentType;

    public AuthenticatedUserDto(String document, String userType, String documentType) {
        this.document = document;
        this.userType = userType;
        this.documentType = documentType;
    }


    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}
