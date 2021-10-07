package org.bilan.co.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateActionsPointsDto{
    private Integer actionId;
    private Integer tribeId;
    private Integer challengeId;
    private Long score;

    List<AnswerRecordDto> answerRecords;
}
