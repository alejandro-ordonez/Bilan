package org.bilan.co.api;

import java.util.List;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.application.IActivityService;
import org.bilan.co.domain.dtos.ActivityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	private IActivityService activityService;

	@GetMapping("/all")
	public ResponseEntity<ResponseDto<List<ActivityDto>>> getAll() {
		List<ActivityDto> activities = activityService.getAll();

		ResponseDto<List<ActivityDto>> response = new ResponseDtoBuilder<List<ActivityDto>>().setResult(activities)
				.setCode(200).setDescription("All activities retrieved successfully").createResponseDto();

		return ResponseEntity.ok(response);

	}
}
