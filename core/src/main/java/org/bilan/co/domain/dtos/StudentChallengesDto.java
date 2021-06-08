package org.bilan.co.domain.dtos;

import java.util.List;

public class StudentChallengesDto {
    private int currentPoints;
    private int challengeId;
    private int tribeId;

    public StudentChallengesDto() {
    }

    public StudentChallengesDto(int currentPoints, int challengeId, int tribeId) {
        this.currentPoints = currentPoints;
        this.challengeId = challengeId;
        this.tribeId = tribeId;
    }

    public int getTribeId() {
        return tribeId;
    }

    public void setTribeId(int tribeId) {
        this.tribeId = tribeId;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }
}
