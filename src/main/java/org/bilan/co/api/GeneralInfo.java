package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.general.IGeneralInfoService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.general.CityDto;
import org.bilan.co.domain.dtos.general.GradeCourseDto;
import org.bilan.co.domain.dtos.general.StateDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.bilan.co.utils.Constants.CACHE_COLLEGES_BY_MUN;

@Slf4j
@RestController
@RequestMapping("/general")
public class GeneralInfo {

    @Autowired
    IGeneralInfoService generalInfoService;

    @GetMapping("states")
    @Cacheable(Constants.STATES)
    public ResponseEntity<ResponseDto<List<StateDto>>> getStates() {
        ResponseDto<List<StateDto>> response = generalInfoService.getStates();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("city")
    @Cacheable(Constants.CITIES)
    public ResponseEntity<ResponseDto<List<CityDto>>> getCities(@RequestParam String state) {
        ResponseDto<List<CityDto>> response = generalInfoService.getCities(state);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("courses")
    @Cacheable(Constants.COURSES)
    public ResponseEntity<ResponseDto<List<GradeCourseDto>>> getCourses() {
        ResponseDto<List<GradeCourseDto>> response = generalInfoService.getCourses();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
