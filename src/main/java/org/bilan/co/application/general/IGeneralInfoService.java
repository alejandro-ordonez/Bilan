package org.bilan.co.application.general;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.general.CityDto;
import org.bilan.co.domain.dtos.general.GradeCourseDto;

import java.util.List;

public interface IGeneralInfoService {
    ResponseDto<List<String>> getStates();

    ResponseDto<List<CityDto>> getCities(String state);

    ResponseDto<List<GradeCourseDto>> getCourses();
}
