package org.bilan.co.domain.dtos.teacher;

import lombok.Data;

import java.util.Date;

@Data
public class EvidencesDto {
    private String document;
    private String name;
    private String lastName;
    private Date uploadedDate;
    private String evidenceId;
}
