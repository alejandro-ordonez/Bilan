package org.bilan.co.domain.dtos.teacher;

import lombok.Data;

@Data
public class EvaluationDto {
    private Integer evidenceId;
    private Integer cbScore;
    private Integer ccScore;
    private Integer csScore;
    private Integer tribeScore;

}
