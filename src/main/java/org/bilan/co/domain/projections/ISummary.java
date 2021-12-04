package org.bilan.co.domain.projections;

public interface ISummary {

    //Score Performance Activity or Score Performance Game
    Integer getScore();

    //Performance Game
    Integer getScoreBasicCompetence();

    //MunicipalityId
    Integer getMunId();

    //CollegeId
    Integer getCollegeId();

    //TribeId
    Integer getId();

    //TribeName
    String getName();

    //Student document
    String getDocument();

    //State Name
    String getState();

}
