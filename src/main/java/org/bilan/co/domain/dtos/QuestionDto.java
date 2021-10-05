package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Integer id;
    private String title;
    private String statement;
    private String shortStatement;
    private String errorMessage;

    @JsonIgnore
    private Integer contextId;

    private List<AnswerDto> answers;
}
