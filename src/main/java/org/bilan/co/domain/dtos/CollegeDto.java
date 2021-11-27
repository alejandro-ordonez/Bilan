package org.bilan.co.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollegeDto {
    private Integer id;
    private String name;
    private String campus;
    private String campusCode;
}

