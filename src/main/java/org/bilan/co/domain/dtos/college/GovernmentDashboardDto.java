package org.bilan.co.domain.dtos.college;

import lombok.Data;

import java.util.List;

@Data
public class GovernmentDashboardDto {
    private Integer students;
    private Integer timeInApp;
    private List<StateDashboardDto> states;

    @Data
    public static class StateDashboardDto {
        private String id;
        private String name;
        private List<ModuleDashboardDto> modules;
    }
}
