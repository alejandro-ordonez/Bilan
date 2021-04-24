package org.bilan.co.domain.dtos;

public class AuthenticatedUserDto {
    private String Document;
    private String UserType;
    private String DocumentType;

    public AuthenticatedUserDto(String document, String userType, String documentType) {
        Document = document;
        UserType = userType;
        DocumentType = documentType;
    }


    public String getDocument() {
        return Document;
    }

    public void setDocument(String document) {
        Document = document;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }
}
