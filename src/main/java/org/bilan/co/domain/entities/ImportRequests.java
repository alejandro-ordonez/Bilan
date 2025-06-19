package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.bilan.co.domain.dtos.user.ImportRequestDto;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.ImportType;

import java.time.LocalDateTime;

@Entity
@Data
@JsonIgnoreProperties({"requestor"})
public class ImportRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String importId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    UserInfo requestor;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    ImportStatus status;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    ImportType type;

    Integer collegeId;

    int processed;

    int rejected;

    LocalDateTime created = LocalDateTime.now();

    LocalDateTime modified;

    public ImportRequestDto toDto() {
        ImportRequestDto dto = new ImportRequestDto();
        dto.setCollegeId(this.collegeId);
        dto.setRequestId(this.importId);
        dto.setImportType(this.type);
        dto.setStatus(this.status);
        dto.setProcessed(this.processed);
        dto.setRejected(this.rejected);
        dto.setCreated(this.created);
        dto.setRequestorId(this.requestor.getDocument());
        return dto;
    }
}
