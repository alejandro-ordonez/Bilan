package org.bilan.co.application;

import org.bilan.co.domain.dtos.BaseSatsDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UpdateStatsDto;
import org.bilan.co.domain.dtos.UserStatsDto;

public interface IStudentStatsService {
    ResponseDto<BaseSatsDto> getUserStats(String token);
    ResponseDto<String> updateUserStats(UpdateStatsDto updateStats, String token);
}
