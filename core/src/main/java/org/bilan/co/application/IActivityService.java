package org.bilan.co.application;

import java.util.List;

import org.bilan.co.domain.dtos.ActivityDto;

public interface IActivityService {

	List<ActivityDto> getAll();
}
