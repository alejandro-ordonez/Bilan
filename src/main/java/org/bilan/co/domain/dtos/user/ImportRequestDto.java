package org.bilan.co.domain.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.ImportType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ImportRequestDto {
    ImportStatus status;
    ImportType importType;
    Integer collegeId;
    int processed;
    int rejected;
    private String requestId;
    private String requestorId;
    private LocalDateTime created;

    public ImportRequestDto(ImportStatus status) {
        this.status = status;
    }
}
