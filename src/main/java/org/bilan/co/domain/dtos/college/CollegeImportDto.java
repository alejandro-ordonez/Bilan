package org.bilan.co.domain.dtos.college;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.dtos.ImportIdentifier;
import org.bilan.co.domain.dtos.college.enums.CollegeImportIndexes;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeImportDto extends ImportIdentifier {
    @NotEmpty(message = "Cod dane municipio no puede estar vacío")
    private String codDaneMunicipality;

    @NotEmpty(message = "Nombre del colegio no puede estar vacío")
    private String name;

    @NotEmpty(message = "Código dane de la sede no puede estar vacío")
    private String codDaneSede;

    @NotEmpty(message = "Nombre de la sede no puede estar vacío")
    private String campusName;


    public static CollegeImportDto readFromStringArray(String[] values) {
        var college = new CollegeImportDto();

        college.codDaneMunicipality = values[CollegeImportIndexes.CodDaneMunicipality.ordinal()].trim();
        college.name = values[CollegeImportIndexes.Name.ordinal()].trim();
        college.codDaneSede = values[CollegeImportIndexes.CodDaneSede.ordinal()].trim();
        college.campusName = values[CollegeImportIndexes.CampusName.ordinal()].trim();

        return college;
    }

    @Override
    public String getIdentifier() {
        return codDaneSede;
    }
}
