package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserType {
    Teacher("TEACHER"),
    DirectiveUser("DIRECT_TEACHER"),
    Student("STUDENT"),
    Min("SEC_EDU"),
    Admin("ADMIN"),
    Unknown("");
    private final String role;

    public static UserType findByRol(String rol) {
        return Arrays.stream(UserType.values())
                .filter(type -> rol.equals(type.getRole()))
                .findFirst()
                .orElse(UserType.Unknown);
    }
}
