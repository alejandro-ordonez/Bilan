package org.bilan.co.domain.dtos.game;

import lombok.Data;
import org.bilan.co.domain.enums.GameCycleStatus;

import java.util.Date;
import java.util.List;

@Data
public class GameCycleDto {
    private String gameId;
    private Date startDate;
    private Date endDate;
    private GameCycleStatus gameStatus;

    private List<String> fileNames;
}
