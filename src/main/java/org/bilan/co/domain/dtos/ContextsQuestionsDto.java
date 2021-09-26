package org.bilan.co.domain.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ContextsQuestionsDto {

    private String content;
    private List<QuestionDto> questionList;
}
