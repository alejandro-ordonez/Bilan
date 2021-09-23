package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.QuestionDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @GetMapping
    public ResponseEntity<ResponseDto<List<QuestionDto>>> getQuestions(@RequestParam("idTribe") int idTribe,
                                                                       @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        return null;
    }
}
