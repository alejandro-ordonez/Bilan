package org.bilan.co.domain.dtos;

import org.bilan.co.domain.dtos.enums.DocumentType;

public class AuthenticatedUserDto {
    private String document;
    private String userType;
    private DocumentType documentType;

    public AuthenticatedUserDto(String document, String userType, DocumentType documentType) {
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

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
