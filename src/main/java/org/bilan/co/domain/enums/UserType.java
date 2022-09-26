package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserType {
    Teacher("TEACHER",2),
    DirectiveTeacher("DIRECT_TEACHER", 3),
    Student("STUDENT", 1),
    MinUser("MIN_USER", 6),
    Admin("ADMIN", 5),
    SecEdu("SEC_EDU", 4),
    Unknown("", 0);
    private final String role;
    private int id;

    public int getId() {
        return id;
    }

    public static UserType findByRol(String rol) {
        return Arrays.stream(UserType.values())
                .filter(type -> rol.equals(type.getRole()))
                .findFirst()
                .orElse(UserType.Unknown);
    }
}
