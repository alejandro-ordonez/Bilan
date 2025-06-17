package org.bilan.co.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentChallengesDto {
    private int currentPoints;
    private int challengeId;
    private int actionId;
    private int tribeId;

    public StudentChallengesDto() {
    }

    public StudentChallengesDto(int currentPoints, int challengeId, int actionId, int tribeId) {
        this.currentPoints = currentPoints;
        this.challengeId = challengeId;
        this.tribeId = tribeId;
        this.actionId = actionId;
    }

}
