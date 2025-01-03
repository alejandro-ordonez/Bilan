package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class CollegeDashboardDto {
    private final Integer students;
    private final Integer totalForumAnswers;
    private final Integer timeInApp;
    private final List<RowSummary<TribeSummaryDto>> data;
}
