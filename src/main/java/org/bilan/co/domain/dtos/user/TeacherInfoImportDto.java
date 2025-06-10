package org.bilan.co.domain.dtos.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.NotImplementedException;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherInfoImportDto extends ImportIdentifier {

    public static TeacherInfoImportDto readFromStringArray(String[] values){
        throw new NotImplementedException();
    }
}
