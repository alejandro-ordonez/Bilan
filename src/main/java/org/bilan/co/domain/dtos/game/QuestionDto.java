package org.bilan.co.domain.dtos.game;

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
    private String context;
    private String statement;
    private String errorMessage;
    private String justification;

    @JsonIgnore
    private Integer contextId;

    private List<AnswerDto> answers;
}
