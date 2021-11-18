package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.game.IStudentStatsService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.game.GameStatsDto;
import org.bilan.co.domain.dtos.game.UpdateStatsDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private IStudentStatsService statsService;

    @GetMapping
    public ResponseEntity<ResponseDto<GameStatsDto>> getStats(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        return  ResponseEntity.ok(statsService.getUserStats(jwt));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> updateStats(
            @Validated @RequestBody UpdateStatsDto updateStats,
            @RequestHeader(Constants.AUTHORIZATION) String jwt){
        return ResponseEntity.ok(statsService.updateUserStats(updateStats, jwt));
    }
}
