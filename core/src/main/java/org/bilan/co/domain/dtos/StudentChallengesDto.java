package org.bilan.co.domain.dtos;

import java.util.List;

public class StudentChallengesDto {
    private int currentPoints;
    private int challengeId;

    public StudentChallengesDto() {
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
