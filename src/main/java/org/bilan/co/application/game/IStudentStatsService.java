package org.bilan.co.application.game;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.game.GameStatsDto;
import org.bilan.co.domain.dtos.game.UpdateStatsDto;

public interface IStudentStatsService {
    ResponseDto<GameStatsDto> getUserStats(String token);
    ResponseDto<String> updateUserStats(UpdateStatsDto updateStats, String token);
}
