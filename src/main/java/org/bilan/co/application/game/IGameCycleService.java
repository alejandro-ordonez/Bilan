package org.bilan.co.application.game;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.game.GameCycleDto;
import org.bilan.co.domain.utils.Tuple;

import java.util.Optional;

public interface IGameCycleService {
    ResponseDto<GameCycleDto> resetGame(String jwt);
    ResponseDto<GameCycleDto> getCurrentCycle();
    ResponseDto<PagedResponse<GameCycleDto>> getCycles(int pageNumber);

    Optional<Tuple<byte[], String>> getReportFile(String cycleId, String fileName);
}
