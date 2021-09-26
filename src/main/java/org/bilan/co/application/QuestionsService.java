package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.*;
import org.bilan.co.domain.entities.Questions;
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
    private Mapper mapper;

    @Override
    public ResponseDto<List<ContextsQuestionsDto>> getQuestions(String token) {

        return null;
    }

    @Override
    public ResponseDto<List<ContextsQuestionsDto>> getQuestions(QuestionRequestDto questionRequestDto, String token) {
        AuthenticatedUserDto authenticatedUser = jwtTokenUtil.getInfoFromToken(token);

        String grade = studentsRepository.getGrade(authenticatedUser.getDocument());

        List<List<Questions>> questions = questionsRepository.getQuestions(PageRequest.of(0,
                        questionRequestDto.getNumberOfQuestions()),
                        grade, questionRequestDto.getTribeId());

        List<ContextsQuestionsDto> contextQuestions = new ArrayList<>();

        questions.forEach(qs -> {
            ContextsQuestionsDto contextQuestion = new ContextsQuestionsDto();
            contextQuestion.setContent(qs.get(0).getContexts().getContent());

            List<QuestionDto> questionsDto = qs.stream().map(q -> new QuestionDto(q.getId(),
                            q.getTitle(),
                            q.getStatement(),
                            q.getShortStatement(),
                            0,
                            q.getAnswersList().stream().map(a -> mapper.map(a, AnswerDto.class)).collect(Collectors.toList())))
                    .collect(Collectors.toList());
            
            contextQuestion.setQuestionList(questionsDto);

            contextQuestions.add(contextQuestion);
        });

        return new ResponseDto<>("", 200, contextQuestions);
    }

    @Override
    public ResponseDto<Boolean> validateAnswer(QuestionDto questionDto) {
        return null;
    }
}
