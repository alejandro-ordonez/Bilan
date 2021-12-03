package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class GovernmentDashboardDto {
    private final Integer students;
    private final Integer timeInApp;
    private final List<StateDashboardDto> data;

    @Data
    @JsonInclude(NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StateDashboardDto {
        private String id;
        private String name;
        private List<TribeSummaryDto> modules;
    }
}
