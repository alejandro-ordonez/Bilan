package org.bilan.co.application;

import org.bilan.co.domain.dtos.ActivityDto;

import java.util.List;

public interface IActivityService {

	List<ActivityDto> getAll();
}
