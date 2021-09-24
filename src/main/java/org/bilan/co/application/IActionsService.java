package org.bilan.co.application;

import org.bilan.co.domain.dtos.ActionDto;

import java.util.List;


public interface IActionsService {
    List<ActionDto> getAll();
    List<ActionDto> getAllByTribe(Integer tribeId);
}
