package org.bilan.co.domain.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

@Data
public class RegisterUserDto extends AuthDto {

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    private String email;
    private String grade;
    private String codDane;
    private String codMunicipio;
    private String selectedState;

    public RegisterUserDto(String document, DocumentType documentType, UserType userType, String password, String name,
                           String lastName, String email, String grade, Integer course) {
        super(document, documentType, userType, password);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.courseId = course;
    }
}
