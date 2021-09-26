package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.IQuestionsService;
import org.bilan.co.domain.dtos.ContextsQuestionsDto;
import org.bilan.co.domain.dtos.QuestionDto;
import org.bilan.co.domain.dtos.QuestionRequestDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private IQuestionsService questionsService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<ContextsQuestionsDto>>> getQuestions(@RequestBody QuestionRequestDto questionRequestDto,
                                                                                @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        return ResponseEntity.ok(questionsService.getQuestions(questionRequestDto, jwt));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Boolean>> validateAnswer(QuestionDto questionDto){
        return ResponseEntity.ok(questionsService.validateAnswer(questionDto));
    }
}
