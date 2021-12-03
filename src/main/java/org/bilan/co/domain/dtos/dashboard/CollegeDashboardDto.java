package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class CollegeDashboardDto {
    private Integer students;
    private Integer totalForumAnswers;
    private Integer timeInApp;
    private List<TribeSummaryDto> data;
}
