package org.bilan.co.domain.projections;

public interface IPerformanceActivity extends ISummary{

    //Solve phase interactive
    Integer getInteractive();

    //Solve phase pre-active
    Integer getPreActive();

    //Solve phase post-active
    Integer getPostActive();
}
