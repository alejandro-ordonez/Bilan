package org.bilan.co.application.general;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.general.CityDto;
import org.bilan.co.domain.dtos.general.GradeCourseDto;
import org.bilan.co.domain.dtos.general.StateDto;
import org.bilan.co.domain.entities.Courses;
import org.bilan.co.domain.entities.StateMunicipality;
import org.bilan.co.infraestructure.persistance.CoursesRepository;
import org.bilan.co.infraestructure.persistance.StateMunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GeneralInfoService implements IGeneralInfoService {

    @Autowired
    StateMunicipalityRepository stateMunicipalityRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @Override
    public ResponseDto<List<StateDto>> getStates() {
        Map<String, List<StateMunicipality>> states = stateMunicipalityRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(StateMunicipality::getState));

        List<StateDto> result = new ArrayList<>();

        for (Map.Entry<String, List<StateMunicipality>> entry : states.entrySet()) {
            List<CityDto> cities = entry.getValue()
                    .stream()
                    .map(sm -> new CityDto(sm.getId(), sm.getMunicipality()))
                    .toList();
            result.add(new StateDto(entry.getKey(), cities));
        }


        return new ResponseDto<>(
                "States returned",
                result.size() > 0 ? 200 : 204,
                result
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

    @Override
    public ResponseDto<List<GradeCourseDto>> getCourses() {
        List<Courses> courses = coursesRepository.findAll();
        List<String> grades = Arrays.asList("10", "11");
        List<GradeCourseDto> response = new ArrayList<>();

        for (String grade : grades) {
            for (Courses c : courses) {
                response.add(new GradeCourseDto(c.getName(), grade));
            }
        }

        return new ResponseDto<>("Data retrieved", 200, response);
    }
}
