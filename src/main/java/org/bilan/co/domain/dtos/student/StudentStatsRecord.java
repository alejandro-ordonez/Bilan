package org.bilan.co.domain.dtos.student;

import lombok.Data;

import java.util.HashMap;

@Data
public class StudentStatsRecord{
    private String document;
    private String name;
    private String lastName;
    private Integer timeInPlatformPerWeek;
    private HashMap<String, Integer> activityScore;
    private HashMap<String, Integer> gameScore;
}
