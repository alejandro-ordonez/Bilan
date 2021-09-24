package org.bilan.co.application;

import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.bilan.co.infraestructure.persistance.ResolvedAnswerByRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScoreService implements IScoreService{

    @Autowired
    private ResolvedAnswerByRepository answersRepository;

    @Override
    public int getScore(String studentId, Integer tribeId, Integer actionId) {
        /*List<ResolvedAnswerBy> records = answersRepository.getScore(studentId, tribeId, actionId);
        int score = 0;
        for(ResolvedAnswerBy answer : records){
            score += answer.getIdChallenge().getReward();
        }*/

        return answersRepository.getScore(studentId, tribeId, actionId);
    }
}
