package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.*;
import org.bilan.co.domain.entities.Questions;
import org.bilan.co.infraestructure.persistance.AnswersRepository;
import org.bilan.co.infraestructure.persistance.QuestionsRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuestionsService implements IQuestionsService{

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public ResponseDto<List<QuestionDto>> getQuestions(String token) {

        AuthenticatedUserDto authenticatedUser = jwtTokenUtil.getInfoFromToken(token);

        String grade = studentsRepository.getGrade(authenticatedUser.getDocument());

        List<List<Questions>> questions = questionsRepository.getQuestions(grade);

        return buildQuestionsResponse(questions);
    }

    @Override
    public ResponseDto<List<QuestionDto>> getQuestions(QuestionRequestDto questionRequestDto, String token) {
        AuthenticatedUserDto authenticatedUser = jwtTokenUtil.getInfoFromToken(token);

        String grade = studentsRepository.getGrade(authenticatedUser.getDocument());

        //TODO: Add condition to don't repeat questions.

        List<List<Questions>> questions = questionsRepository.getQuestions(PageRequest.of(0,
                        questionRequestDto.getNumberOfQuestions()),
                        grade, questionRequestDto.getTribeId());

        return  buildQuestionsResponse(questions);
    }

    private ResponseDto<List<QuestionDto>> buildQuestionsResponse(List<List<Questions>> questions){
        final List<QuestionDto> questionsDto = new ArrayList<>();

        questions.forEach(qs -> {
            String context = qs.get(0).getContexts().getContent();

            qs.forEach(q -> questionsDto.add(new QuestionDto(q.getId(),
                            q.getTitle(),
                            context,
                            q.getStatement(),
                            q.getErrorMessage(),
                            q.getJustification(),
                            0,
                            q.getAnswersList().stream().map(a -> mapper.map(a, AnswerDto.class)).collect(Collectors.toList()))));

        });

        return new ResponseDto<>("", 200, questionsDto);
    }

    @Override
    public ResponseDto<Boolean> validateAnswer(ValidateQuestionDto questionDto) {
        //Brings just the right answers according to the questions
        List<Integer> answers = answersRepository.getAnswersByQuestion(questionDto.getQuestionId());
        //Validates that all the given answers match the right answers
        boolean result = answers.contains(questionDto.getAnswer());

        return new ResponseDto<>("Answers validated", 200, result);
    }
}
