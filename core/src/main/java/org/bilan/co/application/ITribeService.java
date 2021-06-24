package org.bilan.co.application;

import java.util.List;

import org.bilan.co.domain.dtos.TribeDto;

public interface ITribeService {

  List<TribeDto> getAll(String token);
}
