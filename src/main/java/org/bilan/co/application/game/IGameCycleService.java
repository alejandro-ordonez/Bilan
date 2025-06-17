package org.bilan.co.application.game;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.game.GameCycleDto;

public interface IGameCycleService {
    ResponseDto<GameCycleDto> resetGame();
    ResponseDto<GameCycleDto> getCurrentCycle();
    ResponseDto<PagedResponse<GameCycleDto>> getCycles(int pageNumber);
}
