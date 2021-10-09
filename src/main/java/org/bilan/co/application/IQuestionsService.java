package org.bilan.co.application;


import org.bilan.co.domain.dtos.QuestionDto;
import org.bilan.co.domain.dtos.QuestionRequestDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ValidateQuestionDto;

import java.util.List;

public interface IQuestionsService {
    ResponseDto<List<QuestionDto>> getQuestions(String token);
    ResponseDto<List<QuestionDto>> getQuestions(QuestionRequestDto questionRequestDto, String token);
    ResponseDto<Boolean> validateAnswer(ValidateQuestionDto questionDto);
}
