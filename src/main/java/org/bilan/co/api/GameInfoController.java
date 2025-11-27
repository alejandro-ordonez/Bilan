package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.game.IGameInfoService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.game.GameInfoDto;
import org.bilan.co.utils.Constants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/game-info")
public class GameInfoController {

    private final IGameInfoService gameInfoService;

    public GameInfoController(IGameInfoService gameInfoService) {
        this.gameInfoService = gameInfoService;
    }

    @GetMapping
    @Cacheable(Constants.GAME_INFO)
    public ResponseEntity<ResponseDto<GameInfoDto>> getAll() {
        GameInfoDto gameInfoDto = gameInfoService.getGameInfo();

        ResponseDto<GameInfoDto> response = new ResponseDtoBuilder<GameInfoDto>()
                .setResult(gameInfoDto)
                .setCode(200)
                .setDescription("All tribes retrieved successfully").createResponseDto();
        return ResponseEntity.ok(response);
    }
}
