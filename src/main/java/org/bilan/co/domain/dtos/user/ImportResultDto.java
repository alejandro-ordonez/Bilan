package org.bilan.co.domain.dtos.user;

import lombok.Data;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.RejectedUser;
import org.bilan.co.domain.enums.BucketName;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportResultDto {
    ImportStatus status;
    String importId;
    int acceptedCount;
    boolean hasRejectedFile;
    List<RejectedUser> rejectedUsers = new ArrayList<>();
    private BucketName bucket;

    public ImportResultDto(ImportStatus status) {
        this.status = status;
    }

    public void addRejected(RejectedUser user) {
        this.rejectedUsers.add(user);
    }

    public int getRejectedCount() {
        return this.rejectedUsers.size();
    }
}
