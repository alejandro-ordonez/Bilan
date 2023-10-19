package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
@AllArgsConstructor
public class StudentDashboardDto {
    private final Integer percentage;
    private String name;
    @JsonIgnore
    private final Integer percentageGame;
    private final Integer timeInApp;
    private final List<TribeSummaryStudentDto> data;
}
