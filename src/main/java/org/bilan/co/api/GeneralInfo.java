package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.general.IGeneralInfoService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.general.CityDto;
import org.bilan.co.domain.dtos.general.GradeCourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/general")
public class GeneralInfo {

    @Autowired
    IGeneralInfoService generalInfoService;

    @GetMapping("states")
    public ResponseEntity<ResponseDto<List<String>>> getStates() {
        ResponseDto<List<String>> response = generalInfoService.getStates();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("city")
    public ResponseEntity<ResponseDto<List<CityDto>>> getCities(@RequestParam String state) {
        ResponseDto<List<CityDto>> response = generalInfoService.getCities(state);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("courses")
    public ResponseEntity<ResponseDto<List<GradeCourseDto>>> getCourses() {
        ResponseDto<List<GradeCourseDto>> response = generalInfoService.getCourses();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
