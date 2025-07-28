package org.bilan.co.domain.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.ImportIdentifier;
import org.bilan.co.domain.dtos.user.enums.TeacherImportIndexes;
import org.bilan.co.domain.enums.DocumentType;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfoImportDto extends ImportIdentifier {

    @NotNull
    protected DocumentType documentType;

    @NotEmpty(message = "El documento no puede estar vacío")
    @Pattern(regexp = "[1-9][0-9]{5,12}")
    String document;

    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "El correo no cumple el formato")
    private String email;

    @NotEmpty()
    private String name;

    @NotEmpty()
    private String lastName;

    public static TeacherInfoImportDto readFromStringArray(String[] values){
        var document = values[TeacherImportIndexes.Document.ordinal()].trim();
        var documentType = DocumentType.valueOf(values[TeacherImportIndexes.DocumentType.ordinal()].trim());
        var email = values[TeacherImportIndexes.Email.ordinal()].trim();
        var name = values[TeacherImportIndexes.Name.ordinal()].trim();
        var lastName = values[TeacherImportIndexes.LastName.ordinal()].trim();

        var teacher = new TeacherInfoImportDto();

        teacher.document = document;
        teacher.documentType = documentType;
        teacher.email = email;
        teacher.name = name;
        teacher.lastName = lastName;

        return teacher;
    }

    @Override
    public String getIdentifier() {
        return document;
    }
}
