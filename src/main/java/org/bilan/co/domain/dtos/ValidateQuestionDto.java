package org.bilan.co.domain.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ValidateQuestionDto {

    List<Integer> answers;
    private int questionId;
}
