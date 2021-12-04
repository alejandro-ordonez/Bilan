package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class TribeSummaryStudentDto extends TribeSummaryDto {
    private Integer timeInApp;
    private Integer preActivePhase;
    private Integer postActivePhase;
    private Integer interactivePhase;

    public TribeSummaryStudentDto cleanPhases() {
        this.preActivePhase = null;
        this.postActivePhase = null;
        this.interactivePhase = null;
        return this;
    }
}
