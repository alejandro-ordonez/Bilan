package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
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

    String acceptedFilePath;

    String rejectedFilePath;

    LocalDateTime created = LocalDateTime.now();

    LocalDateTime modified;
}
