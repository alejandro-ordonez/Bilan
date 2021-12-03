package org.bilan.co.domain.projections;

public interface IPerformanceActivity {

    //MunicipalityId
    Integer getMunId();

    //MunicipalityName
    String getMunName();

    //CollegeId
    Integer getCollegeId();

    //CollegeName
    String getCollegeName();

    //TribeId
    Integer getId();

    //TribeName
    String getName();

    //Student document
    String getDocument();

    //State Name
    String getState();

    Integer getScorePerformanceActivity();

    Integer getScoreThinkingCompetence();
}
