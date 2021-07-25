package org.bilan.co.application;

import java.util.List;
import java.util.stream.Collectors;

import org.bilan.co.domain.dtos.ActivityDto;
import org.bilan.co.domain.entities.Activities;
import org.bilan.co.infraestructure.persistance.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActivityService implements IActivityService {

	@Autowired
	private ActivitiesRepository activitiesRepository;

	@Override
	public List<ActivityDto> getAll() {

		List<Activities> activities = activitiesRepository.findAll();
		List<ActivityDto> activitiesDto = activities.stream().map(ActivityDto::new).collect(Collectors.toList());

		return activitiesDto;
	}
}
