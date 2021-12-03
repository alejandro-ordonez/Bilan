package org.bilan.co.domain.projections;

public interface IPerformanceActivity extends ISummary{

    //Performance Activity
    Integer getScorePerformanceActivity();

    //Performance Game
    Integer getScoreBasicCompetence();

    //Solve phase interactive
    Integer getInteractive();

    Integer getPreActive();

    Integer getPostActive();
}
