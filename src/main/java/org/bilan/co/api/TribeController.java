package org.bilan.co.api;

import java.util.List;

import org.bilan.co.application.ITribeService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.TribeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tribes")
public class TribeController {

  @Autowired
  private ITribeService tribeService;

  @GetMapping("/all")
  public ResponseEntity<ResponseDto<List<TribeDto>>> getAll() {
    List<TribeDto> tribes = tribeService.getAll();

    ResponseDto<List<TribeDto>> response = new ResponseDtoBuilder<List<TribeDto>>()
            .setResult(tribes)
            .setCode(200)
            .setDescription("All tribes retrieved successfully").createResponseDto();

    return ResponseEntity.ok(response);
  }

}
