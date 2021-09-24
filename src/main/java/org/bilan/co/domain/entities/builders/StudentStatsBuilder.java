package org.bilan.co.domain.entities.builders;

import org.bilan.co.domain.entities.StudentStats;

import java.util.Date;
import java.util.List;

public class StudentStatsBuilder {
    private Integer id;
    private Date currentCycleEnd;
    private Integer generalTotems;
    private Integer analyticalTotems;
    private Integer criticalTotems;
    private Integer currentCycle;
    private List<StudentActions> studentChallengesList;

    public StudentStatsBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public StudentStatsBuilder setCurrentCycleEnd(Date currentCycleEnd) {
        this.currentCycleEnd = currentCycleEnd;
        return this;
    }

    public StudentStatsBuilder setGeneralTotems(Integer generalTotems) {
        this.generalTotems = generalTotems;
        return this;
    }

    public StudentStatsBuilder setAnalyticalTotems(Integer analyticalTotems) {
        this.analyticalTotems = analyticalTotems;
        return this;
    }

    public StudentStatsBuilder setCriticalTotems(Integer criticalTotems) {
        this.criticalTotems = criticalTotems;
        return this;
    }

    public StudentStatsBuilder setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
        return this;
    }

    public StudentStatsBuilder setStudentChallengesList(List<StudentActions> studentChallengesList) {
        this.studentChallengesList = studentChallengesList;
        return this;
    }

    public StudentStats createStudentStats() {
        return new StudentStats(generalTotems, analyticalTotems, criticalTotems, currentCycle, currentCycleEnd, studentChallengesList);
    }
}