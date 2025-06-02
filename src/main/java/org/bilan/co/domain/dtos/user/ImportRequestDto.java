package org.bilan.co.domain.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.ImportType;

@Data
@NoArgsConstructor
public class ImportRequestDto {
    ImportStatus status;
    ImportType importType;
    int collegeId;
    int processed;
    int rejected;
    String rejectedFilePath;
    private String requestId;
    private String requestorId;
}
