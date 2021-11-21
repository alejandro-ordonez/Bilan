package org.bilan.co.application;


import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.game.QuestionDto;
import org.bilan.co.domain.dtos.game.QuestionRequestDto;
import org.bilan.co.domain.dtos.game.ValidateQuestionDto;

import java.util.List;

public interface IQuestionsService {
    ResponseDto<List<QuestionDto>> getQuestions(String token);
    ResponseDto<List<QuestionDto>> getQuestions(QuestionRequestDto questionRequestDto, String token);
    ResponseDto<Boolean> validateAnswer(ValidateQuestionDto questionDto);
}
