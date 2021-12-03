package org.bilan.co.domain.dtos;

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

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
}
