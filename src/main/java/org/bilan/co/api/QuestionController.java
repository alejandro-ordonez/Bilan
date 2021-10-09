package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.IQuestionsService;
import org.bilan.co.domain.dtos.QuestionDto;
import org.bilan.co.domain.dtos.QuestionRequestDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ValidateQuestionDto;
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

    @PostMapping
    public ResponseEntity<ResponseDto<List<QuestionDto>>> getQuestions(@RequestBody QuestionRequestDto questionRequestDto,
                                                                       @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        return ResponseEntity.ok(questionsService.getQuestions(questionRequestDto, jwt));
    }

    @GetMapping("all")
    public ResponseEntity<ResponseDto<List<QuestionDto>>> getQuestions(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        return ResponseEntity.ok(questionsService.getQuestions(jwt));
    }

    @PostMapping("validate")
    public ResponseEntity<ResponseDto<Boolean>> validateAnswer(@RequestBody ValidateQuestionDto questionDto){
        return ResponseEntity.ok(questionsService.validateAnswer(questionDto));
    }
}
