package org.bilan.co.domain.dtos;

import org.bilan.co.domain.dtos.enums.DocumentType;
import org.bilan.co.domain.dtos.enums.UserType;

import javax.validation.constraints.NotNull;

public class RegisterUserDto extends AuthDto {

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    private String email;

    private String grade;
    private String course;

    public RegisterUserDto(String document, DocumentType documentType, UserType userType, String password, String name, String lastName, String email, String grade, String course) {
        super(document, documentType, userType, password);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.course = course;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
