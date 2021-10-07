package org.bilan.co.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfoDto {
    List<TribeDto> tribes;
    List<ActionDto> actions;
    List<ChallengesDto> challenges;
}
