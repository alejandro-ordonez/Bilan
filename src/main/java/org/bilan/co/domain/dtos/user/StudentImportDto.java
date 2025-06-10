package org.bilan.co.domain.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.user.enums.StudentImportIndexes;
import org.bilan.co.domain.enums.DocumentType;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StudentImportDto extends ImportIdentifier {
    @NotEmpty(message = "El nombre no puede estar vacío")
    String name;

    @NotEmpty(message = "El apellido no puede estar vacío")
    String lastName;

    @NotEmpty(message = "El grado no puede estar vacío")
    @Pattern(regexp = "^(10|11)$", message = "Grado inválido, debe ser 10 o 11")
    String grade;

    @NotEmpty(message = "Curso no válido")
    String course;

    public static StudentImportDto readFromStringArray(String[] values) throws IllegalArgumentException {
        DocumentType documentType = DocumentType.valueOf(values[StudentImportIndexes.DocumentType.ordinal()]);

        StudentImportDto student = new StudentImportDto();
        student.setDocument(values[StudentImportIndexes.Document.ordinal()].trim());
        student.setDocumentType(documentType);
        student.setName(values[StudentImportIndexes.Name.ordinal()].trim());
        student.setLastName(values[StudentImportIndexes.LastName.ordinal()].trim());
        student.setGrade(values[StudentImportIndexes.Grade.ordinal()].trim());
        student.setCourse(values[StudentImportIndexes.Course.ordinal()].trim());

        return student;
    }
}
