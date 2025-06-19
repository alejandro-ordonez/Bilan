package org.bilan.co.domain.dtos.college;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.dtos.ImportIdentifier;
import org.bilan.co.domain.dtos.college.enums.CollegeImportIndexes;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeImportDto extends ImportIdentifier {
    @NotEmpty
    private String codDaneMunicipality;
    @NotEmpty
    private String name;
    @NotEmpty
    private String codDaneSede;
    @NotEmpty
    private String campusName;


    public static CollegeImportDto readFromStringArray(String[] values) {
        var college = new CollegeImportDto();

        college.codDaneMunicipality = values[CollegeImportIndexes.CodDaneMunicipality.ordinal()];
        college.name = values[CollegeImportIndexes.Name.ordinal()];
        college.codDaneSede = values[CollegeImportIndexes.CodDaneSede.ordinal()];
        college.campusName = values[CollegeImportIndexes.CampusName.ordinal()];

        return college;
    }

    @Override
    public String getIdentifier() {
        return codDaneSede;
    }
}
