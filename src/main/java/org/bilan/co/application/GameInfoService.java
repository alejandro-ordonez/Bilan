package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ActionDto;
import org.bilan.co.domain.dtos.ChallengesDto;
import org.bilan.co.domain.dtos.GameInfoDto;
import org.bilan.co.domain.dtos.TribeDto;
import org.bilan.co.infraestructure.persistance.ActionsRepository;
import org.bilan.co.infraestructure.persistance.ChallengesRepository;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameInfoService implements IGameInfoService {

    private final TribesRepository tribesRepository;
    private final ActionsRepository actionsRepository;
    private final ChallengesRepository challengesRepository;
    private final Mapper mapper;

    public GameInfoService(TribesRepository tribesRepository,
                           ActionsRepository actionsRepository,
                           ChallengesRepository challengesRepository,
                           Mapper mapper) {

        this.tribesRepository = tribesRepository;
        this.actionsRepository = actionsRepository;
        this.challengesRepository = challengesRepository;
        this.mapper = mapper;
    }

    @Override
    public GameInfoDto getGameInfo() {

        List<ActionDto> actions = actionsRepository.findAll()
                .stream()
                .map(action -> mapper.map(action, ActionDto.class))
                .collect(Collectors.toList());

        List<TribeDto> tribes = tribesRepository.getAllDtos();

        List<ChallengesDto> challenges = challengesRepository.findAll()
                .stream()
                .map(challenge -> mapper.map(challenge, ChallengesDto.class))
                .collect(Collectors.toList());


        return new GameInfoDto(tribes, actions, challenges);
    }

}
