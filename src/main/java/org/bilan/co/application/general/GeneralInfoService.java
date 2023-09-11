package org.bilan.co.application.general;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.general.CityDto;
import org.bilan.co.infraestructure.persistance.StateMunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GeneralInfoService implements IGeneralInfoService {

    @Autowired
    StateMunicipalityRepository stateMunicipalityRepository;

    @Override
    public ResponseDto<List<String>> getStates() {
        List<String> states = stateMunicipalityRepository.states();

        return new ResponseDto<>(
                "States returned",
                states.size() > 0 ? 200 : 204,
                states
        );
    }

    @Override
    public ResponseDto<List<CityDto>> getCities(String state) {
        List<CityDto> cities = stateMunicipalityRepository.findMunicipalitiesByState(state)
                .stream()
                .map(CityDto::new)
                .collect(Collectors.toList());

        return new ResponseDto<>(
                "Cities returned",
                cities.size() > 0 ? 200 : 204,
                cities
        );
    }
}
