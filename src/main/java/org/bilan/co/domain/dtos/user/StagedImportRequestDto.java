package org.bilan.co.domain.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.enums.BucketName;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StagedImportRequestDto<T> {
    List<T> processed = new ArrayList<>();
    private String importRequestId;
    private ImportType importType;
    private int collegeId;
    private String requestor;
    private BucketName bucket;

    public StagedImportRequestDto(String importRequestId, ImportType importType) {
        this.importRequestId = importRequestId;
        this.importType = importType;
    }

    public void addProcessed(List<T> data) {
        this.processed.addAll(data);
    }
}

