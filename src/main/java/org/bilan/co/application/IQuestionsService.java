package org.bilan.co.application;


import org.bilan.co.domain.dtos.ContextsQuestionsDto;
import org.bilan.co.domain.dtos.QuestionRequestDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ValidateQuestionDto;

import java.util.List;

public interface IQuestionsService {
    ResponseDto<List<ContextsQuestionsDto>> getQuestions(String token);
    ResponseDto<List<ContextsQuestionsDto>> getQuestions(QuestionRequestDto questionRequestDto, String token);
    ResponseDto<Boolean> validateAnswer(ValidateQuestionDto questionDto);
}
