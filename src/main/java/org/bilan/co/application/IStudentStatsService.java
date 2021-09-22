package org.bilan.co.application;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UserStatsDto;

public interface IStudentStatsService {
    ResponseDto<UserStatsDto> getUserStats(String token);
    ResponseDto<String> updateUserStats(UserStatsDto userStatsDto, String token);
}
