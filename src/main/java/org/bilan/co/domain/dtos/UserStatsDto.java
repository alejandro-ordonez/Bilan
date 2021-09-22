package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatsDto {

    private int generalTotems;
    private int analyticalTotems;
    private int criticalTotems;
    private int currentCycle;
    private int currentSpirits;
    private String tribePoints;
    private Date lastTotemUpdate;
    private List<StudentChallengesDto> studentChallenges;

    public UserStatsDto() {
        studentChallenges = new ArrayList<>();
    }

    public String getTribePoints() {
        return tribePoints;
    }

    public void setTribePoints(String tribePoints) {
        this.tribePoints = tribePoints;
    }

    public int getCurrentSpirits() {
        return currentSpirits;
    }

    public void setCurrentSpirits(int currentSpirits) {
        this.currentSpirits = currentSpirits;
    }

    public Date getLastTotemUpdate() {
        return lastTotemUpdate;
    }

    public void setLastTotemUpdate(Date lastTotemUpdate) {
        this.lastTotemUpdate = lastTotemUpdate;
    }

    public int getGeneralTotems() {
        return generalTotems;
    }

    public void setGeneralTotems(int generalTotems) {
        this.generalTotems = generalTotems;
    }

    public int getAnalyticalTotems() {
        return analyticalTotems;
    }

    public void setAnalyticalTotems(int analyticalTotems) {
        this.analyticalTotems = analyticalTotems;
    }

    public int getCriticalTotems() {
        return criticalTotems;
    }

    public void setCriticalTotems(int criticalTotems) {
        this.criticalTotems = criticalTotems;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public List<StudentChallengesDto> getStudentChallenges() {
        return studentChallenges;
    }

    public void setStudentChallenges(List<StudentChallengesDto> studentChallenges) {
        this.studentChallenges = studentChallenges;
    }
}
