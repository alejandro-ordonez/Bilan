package org.bilan.co.application;

import java.util.List;
import java.util.stream.Collectors;

import org.bilan.co.domain.dtos.TribeDto;
import org.bilan.co.domain.entities.Tribes;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TribeService implements ITribeService {

    private final TribesRepository tribesRepository;
    private final Mapper mapper;

    public TribeService(TribesRepository tribesRepository, Mapper mapper) {
        this.tribesRepository = tribesRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TribeDto> getAll() {
        return tribesRepository.findAll()
                .stream()
                .map(tribe -> mapper.map(tribe, TribeDto.class))
                .collect(Collectors.toList());
    }
}
