package org.bilan.co.domain.dtos;

public class RegisterDto {
    private String document;
    private String documentType;
    private String userType;
    private String password;

    RegisterDto(String document, String documentType, String userType, String password){
        this.document = document;
        this.documentType = documentType;
        this.userType = userType;
        this.password = password;
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

    public String getPassword(){
        return password;
    }

}
