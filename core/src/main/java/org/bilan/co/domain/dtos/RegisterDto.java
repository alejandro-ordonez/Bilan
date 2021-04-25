package org.bilan.co.domain.dtos;

public class RegisterDto {
    private String document;
    private String documentType;
    private String userType;

    RegisterDto(String document, String documentType, String userType){
        this.document = document;
        this.documentType = documentType;
        this.userType = userType;
    }

    public String getDocument() {
        return document;
    }

    public String getDocumentType(){
        return documentType;
    }

    public String getUserType(){
        return userType;
    }

}
