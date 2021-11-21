package org.bilan.co.domain.dtos.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.ActionDto;
import org.bilan.co.domain.dtos.TribeDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfoDto {
    List<TribeDto> tribes;
    List<ActionDto> actions;
    List<ChallengesDto> challenges;
}
