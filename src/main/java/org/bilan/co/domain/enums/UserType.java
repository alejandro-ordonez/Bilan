package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserType {
    Teacher("TEACHER"),
    DirectiveTeacher("DIRECT_TEACHER"),
    Student("STUDENT"),
    MinUser("MIN_USER"),
    Admin("ADMIN"),
    SecEdu("SEC_EDU"),
    Unknown("");
    private final String role;

    public static UserType findByRol(String rol) {
        return Arrays.stream(UserType.values())
                .filter(type -> rol.equals(type.getRole()))
                .findFirst()
                .orElse(UserType.Unknown);
    }
}
