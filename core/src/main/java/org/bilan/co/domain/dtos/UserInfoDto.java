package org.bilan.co.domain.dtos;

import org.bilan.co.domain.dtos.enums.DocumentType;

public class UserInfoDto extends AuthenticatedUserDto{

    private String name;
    private String lastName;
    private String email;


    public UserInfoDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
