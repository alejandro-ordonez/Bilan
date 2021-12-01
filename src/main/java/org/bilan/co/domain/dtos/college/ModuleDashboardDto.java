package org.bilan.co.domain.dtos.college;

import lombok.Data;

@Data
public class ModuleDashboardDto {
    private Integer id;
    private String name;
    private Float performanceActivityScore;
    private Integer performanceGameScore;
}
