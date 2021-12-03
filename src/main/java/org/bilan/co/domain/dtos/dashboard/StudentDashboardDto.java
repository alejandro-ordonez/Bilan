package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class StudentDashboardDto {
    private final Integer percentage;
    private final Integer timeInApp;
    private final List<TribeSummaryStudentDto> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(NON_NULL)
    public static class TribeSummaryStudentDto extends TribeSummaryDto {
        private Integer preActivePhase;
        private Integer postActivePhase;
        private Integer interactivePhase;
    }
}
