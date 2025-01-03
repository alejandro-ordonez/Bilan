package org.bilan.co.domain.entities.builders;

import java.util.Date;

public class StudentStatsBuilder {
    private Integer id;
    private Date currentCycleEnd;
    private Integer generalTotems;
    private Integer analyticalTotems;
    private Integer criticalTotems;
    private Integer currentCycle;

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
}