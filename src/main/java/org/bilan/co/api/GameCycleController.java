package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.AllowDuringMaintenance;
import org.bilan.co.application.game.IGameCycleService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.game.GameCycleDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/game-cycle")
@AllowDuringMaintenance
public class GameCycleController {

    @Autowired
    private IGameCycleService gameCycleService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/current")
    public ResponseDto<GameCycleDto> currentGameCycle(){
        return gameCycleService.getCurrentCycle();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/reset-game")
    public ResponseDto<GameCycleDto> resetGame(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
        return gameCycleService.resetGame(jwt);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping()
    public ResponseDto<PagedResponse<GameCycleDto>> getCycles(
            @RequestParam("page") Integer nPage
    ){
        return gameCycleService.getCycles(nPage);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<byte[]> downloadReport(@RequestParam("cycleId") String cycleId,
                                                 @RequestParam("fileName") String fileName) {
        var report = gameCycleService.getReportFile(cycleId, fileName);

        return report.map(data -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, data.getValue2())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".csv")
                        .body(data.getValue1()))
                .orElse(ResponseEntity.notFound().build());
    }
}
