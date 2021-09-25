package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateActionsPointsDto extends ActionsPoints{
    List<AnswerRecordDto> answerRecords;
}
