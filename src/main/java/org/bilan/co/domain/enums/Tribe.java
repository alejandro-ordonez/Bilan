package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tribe {
    SOCIAL_COMPETENCE(1, "Competencias Socioemocionales"),
    LANGUAGE(2, "Lenguaje"),
    NATURAL_SCIENCE(3, "Ciencias Naturales"),
    CITIZEN_COMPETENCE(4, "Competencias Ciudadanas"),
    MATH(5, "Matemáticas");
    private final int id;
    private final String name;

}
