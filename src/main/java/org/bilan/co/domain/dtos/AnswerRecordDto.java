package org.bilan.co.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class AnswerRecordDto {
    private Integer questionId;
    private Integer answerId;
}
