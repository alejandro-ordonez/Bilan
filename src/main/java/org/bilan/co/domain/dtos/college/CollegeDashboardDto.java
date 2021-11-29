package org.bilan.co.domain.dtos.college;

import lombok.Data;
import org.bilan.co.domain.enums.PerformanceScale;

import java.util.List;

@Data
public class CollegeDashboardDto {
    private Integer students;
    private Integer totalForumAnswers;
    private Integer timeInApp;
    private List<ModuleDashboard> modules;

    @Data
    public static class ModuleDashboard {
        private Integer id;
        private String name;
        private Float score;
        private Integer points;
        private PerformanceScale scale;
    }
}
