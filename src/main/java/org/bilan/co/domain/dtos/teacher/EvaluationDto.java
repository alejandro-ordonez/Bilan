package org.bilan.co.domain.dtos.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class EvaluationDto {
    private String student;
    private Long evidenceId;
    private Integer cbScore;
    private Integer ccScore;
    private Integer csScore;
    private Integer tribeScore;
}
