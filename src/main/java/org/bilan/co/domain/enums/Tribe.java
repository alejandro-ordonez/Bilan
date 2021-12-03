package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tribe {
    SOCIAL_COMPETENCE(1, "Competencias Socioemocionales", "CS"),
    LANGUAGE(2, "Lenguaje", "L"),
    NATURAL_SCIENCE(3, "Ciencias Naturales", "CN"),
    CITIZEN_COMPETENCE(4, "Competencias Ciudadanas", "CC"),
    MATH(5, "Matemáticas", "M");
    private final int id;
    private final String name;
    private final String abbreviation;

}
