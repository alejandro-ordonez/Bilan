package org.bilan.co.domain.entities;

import lombok.Data;
import org.bilan.co.domain.enums.GameStatus;

import java.util.Date;

@Data
public class GameCycle {
    int gameId;
    Date startDate;
    Date endDate;
    GameStatus gameStatus;
}
