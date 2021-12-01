package org.bilan.co.domain.dtos.college;

import lombok.Data;

import java.util.List;

@Data
public class CollegeDashboardDto {
    private Integer students;
    private Integer totalForumAnswers;
    private Integer timeInApp;
    private List<ModuleDashboardDto> modules;
}
