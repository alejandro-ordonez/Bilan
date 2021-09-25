package org.bilan.co.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
@Data
public class AnswerRecordDto {
    private Integer challengeId;
    private List<Answer> answers;

    @EqualsAndHashCode
    @Data
    public static class Answer{
        private Integer questionId;
        private Integer answerId;
    }
}
