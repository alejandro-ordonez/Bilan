package org.bilan.co.domain.dtos.user;

import lombok.Data;
import org.bilan.co.domain.dtos.user.enums.ImportType;

import java.util.List;

@Data
public class ImportRecordDto {
    private ImportType importType;
    private List<String> importIds;

    public ImportRecordDto(ImportType importType, List<String> importIds) {
        this.importType = importType;
        this.importIds = importIds;
    }
}
