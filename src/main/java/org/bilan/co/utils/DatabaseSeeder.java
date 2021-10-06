package org.bilan.co.utils;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.entities.*;
import org.bilan.co.infraestructure.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Component
public class DatabaseSeeder {

    @Autowired
    private TribesRepository tribesRepository;
    @Autowired
    private ActionsRepository actionsRepository;
    @Autowired
    private ChallengesRepository challengesRepository;
    @Autowired
    private AnswersRepository answersRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private ContextRepository contextRepository;

    @EventListener
    public void seedDatabase(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Seed database triggered");
       /* actionsRepository.deleteAll();
        tribesRepository.deleteAll();
        challengesRepository.deleteAll();
        questionsRepository.deleteAll();*/

        seedTribes();
    }

    private void seedQuestions(Tribes tribes) {

        Contexts contexts = new Contexts();
        contexts.setContent("Content 0");
        contexts.setQuestions(new ArrayList<>());

        for(int i = 1; i<=20; i++){

            if(i%5==0){
                contexts = new Contexts();
                contexts.setContent("Content "+ i);
                contexts.setQuestions(new ArrayList<>());
            }

            contextRepository.save(contexts);
            Questions question = new Questions();
            question.setClueChaman("ClueChaman "+i);
            question.setDifficulty(i);
            question.setStatement("Statementttt");
            question.setTitle("Question Title");
            question.setGrade("10");
            question.setIdTribe(tribes);
            question.setContexts(contexts);


            question.setAnswersList(new ArrayList<>());
            questionsRepository.save(question);

            for(int j = 0; j<4; j++){
                Answers answer = new Answers();
                answer.setStatement("asdfasdf");
                answer.setIsCorrect(true);
                answer.setIdQuestion(question);
                question.getAnswersList().add(answer);
                answersRepository.save(answer);
            }
        }
    }

    public void seedActions(Tribes tribe) {

        if(tribesRepository.count()>0)
            return;

        Actions spiritualAction = new Actions();
        spiritualAction.setTribe(tribe);
        spiritualAction.setName("Espiritual");
        spiritualAction.setRepresentative("No sé");

        Actions explorerAction = new Actions();
        explorerAction.setTribe(tribe);
        explorerAction.setName("Exploración");
        explorerAction.setRepresentative("?");

        Actions civilAction = new Actions();
        civilAction.setTribe(tribe);
        civilAction.setName("Civil");
        civilAction.setRepresentative("?");

        actionsRepository.saveAll(Arrays.asList(spiritualAction, explorerAction, civilAction));

        seedChallenge(spiritualAction);
        seedChallenge(explorerAction);
        seedChallenge(civilAction);
    }

    public void seedChallenge(Actions actions){
        actions.setChallenges(new ArrayList<>());

        for(int i=0; i<10; i++)
        {
            Challenges challenges = new Challenges();
            challenges.setName("Challenge "+i);
            challenges.setAction(actions);
            challenges.setCost(i);
            challenges.setReward(i*4);
            challenges.setTimer(100);
            challenges.setType("asdf");

            actions.getChallenges().add(challenges);
        }
        challengesRepository.saveAll(actions.getChallenges());
    }

    public void seedTribes() {
        log.info("Seeding tribes");

        if(tribesRepository.count()>0)
            return;

        Tribes fireTribe = new Tribes();
        fireTribe.setElement("Fuego");
        fireTribe.setName("Fire Tribe");

        Tribes earthTribe = new Tribes();
        earthTribe.setElement("Tierra");
        earthTribe.setName("Earth tribe");

        Tribes waterTribe = new Tribes();
        waterTribe.setElement("Agua");
        waterTribe.setName("Water tribe");

        Tribes airTribe = new Tribes();
        airTribe.setElement("Aire");
        airTribe.setName("Air tribe");

        Tribes lightTribe = new Tribes();
        lightTribe.setElement("Light");
        lightTribe.setName("light tribe");

        tribesRepository.save(fireTribe);
        tribesRepository.save(earthTribe);
        tribesRepository.save(waterTribe);
        tribesRepository.save(airTribe);
        tribesRepository.save(lightTribe);

        lightTribe.setAdjacentTribeId(earthTribe);
        lightTribe.setOppositeTribeId(waterTribe);

        earthTribe.setAdjacentTribeId(waterTribe);
        earthTribe.setOppositeTribeId(airTribe);

        waterTribe.setAdjacentTribeId(airTribe);
        waterTribe.setOppositeTribeId(fireTribe);

        airTribe.setAdjacentTribeId(fireTribe);
        airTribe.setOppositeTribeId(lightTribe);

        fireTribe.setAdjacentTribeId(lightTribe);
        fireTribe.setOppositeTribeId(earthTribe);

        tribesRepository.save(fireTribe);
        tribesRepository.save(earthTribe);
        tribesRepository.save(waterTribe);
        tribesRepository.save(airTribe);
        tribesRepository.save(lightTribe);

        seedActions(fireTribe);
        seedActions(earthTribe);
        seedActions(waterTribe);
        seedActions(airTribe);
        seedActions(lightTribe);

        seedQuestions(fireTribe);
    }
}
