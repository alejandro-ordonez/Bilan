package org.bilan.co.application;

import org.bilan.co.domain.dtos.ActionDto;
import org.bilan.co.infraestructure.persistance.ActionsRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionsService implements IActionsService{

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<ActionDto> getAll() {
        return actionsRepository.findAll()
                .stream()
                .map(action -> mapper.map(action, ActionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActionDto> getAllByTribe(Integer tribeId) {
        return actionsRepository.findAllByTribe(tribeId)
                .stream()
                .map(action -> mapper.map(action, ActionDto.class))
                .collect(Collectors.toList());
    }
}