package org.bilan.co.domain.dtos.college;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.dtos.ImportIdentifier;
import org.bilan.co.domain.dtos.college.enums.CollegeImportIndexes;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeImportDto extends ImportIdentifier {
    private String codDaneMunicipality;
    private String name;
    private String codDaneSede;
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
