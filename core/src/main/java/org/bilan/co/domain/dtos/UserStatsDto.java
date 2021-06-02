package org.bilan.co.domain.dtos;

import java.util.Date;

public class UserStatsDto {

    private int generalTotems;
    private int analyticalTotems;
    private int criticalTotems;
    private int currentCycle;
    private Date currentCycleEnd;
    private StudentChallengesDto studentChallengesDto;

    public UserStatsDto() {
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

    public Date getCurrentCycleEnd() {
        return currentCycleEnd;
    }

    public void setCurrentCycleEnd(Date currentCycleEnd) {
        this.currentCycleEnd = currentCycleEnd;
    }

    public StudentChallengesDto getStudentChallengesDto() {
        return studentChallengesDto;
    }

    public void setStudentChallengesDto(StudentChallengesDto studentChallengesDto) {
        this.studentChallengesDto = studentChallengesDto;
    }
}
