package org.bilan.co.application;

import java.util.List;
import java.util.stream.Collectors;
import org.bilan.co.domain.dtos.TribeDto;
import org.bilan.co.domain.entities.Tribes;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TribeService implements ITribeService {

  @Autowired
  private TribesRepository tribesRepository;

  @Override
  public List<TribeDto> getAll() {
    List<Tribes> tribes = tribesRepository.findAll();
    List<TribeDto> tribesDto = tribes.stream().map(TribeDto::new).collect(Collectors.toList());

    return tribesDto;
  }
}
