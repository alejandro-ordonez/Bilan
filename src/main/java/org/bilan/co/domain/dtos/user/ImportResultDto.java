package org.bilan.co.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.RejectedRow;
import org.bilan.co.domain.enums.BucketName;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportResultDto {
    ImportStatus status;
    String importId;
    int acceptedCount;
    boolean hasRejectedFile;
    List<RejectedRow> rejectedRows = new ArrayList<>();

    @JsonIgnore
    private BucketName bucket;

    @JsonIgnore
    private String headers;

    public ImportResultDto(ImportStatus status) {
        this.status = status;
    }

    public void addRejected(RejectedRow user) {
        this.rejectedRows.add(user);
    }

    public int getRejectedCount() {
        return this.rejectedRows.size();
    }
}
