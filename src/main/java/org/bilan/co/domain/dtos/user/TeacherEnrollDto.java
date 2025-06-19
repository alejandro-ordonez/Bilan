package org.bilan.co.domain.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.dtos.ImportIdentifier;
import org.bilan.co.domain.dtos.user.enums.TeacherEnrollmentIndexes;
import org.bilan.co.domain.enums.DocumentType;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherEnrollDto extends ImportIdentifier {
    @NotNull
    protected DocumentType documentType;

    @NotEmpty(message = "El documento no puede estar vacío")
    @Pattern(regexp = "[1-9][0-9]{5,12}")
    String document;

    @NotEmpty(message = "El grado no puede estar vacío")
    @Pattern(regexp = "^(10|11)$", message = "Grado inválido, debe ser 10 o 11")
    String grade;

    @NotEmpty(message = "Curso no válido")
    String course;

    @NotEmpty(message = "Tribu no asignada")
    String tribe;

    public static TeacherEnrollDto readFromStringArray(String[] values) throws IllegalArgumentException {
        DocumentType documentType = DocumentType.valueOf(values[TeacherEnrollmentIndexes.DocumentType.ordinal()]);

        TeacherEnrollDto teacher = new TeacherEnrollDto();
        teacher.setDocument(values[TeacherEnrollmentIndexes.Document.ordinal()]);
        teacher.setDocumentType(documentType);
        teacher.setGrade(values[TeacherEnrollmentIndexes.Grade.ordinal()]);
        teacher.setCourse(values[TeacherEnrollmentIndexes.Course.ordinal()]);
        teacher.setTribe(values[TeacherEnrollmentIndexes.Tribe.ordinal()]);

        return teacher;
    }

    @Override
    public String getIdentifier() {
        return document;
    }
}
