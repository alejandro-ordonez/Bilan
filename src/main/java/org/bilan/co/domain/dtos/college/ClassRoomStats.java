package org.bilan.co.domain.dtos.college;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class ClassRoomStats {
    private Float groupProgress;
    private Integer students;

    private List<StudentStatsRecord> studentStatsRecords;

    @Data
    public static class StudentStatsRecord{
        private String document;
        private String name;
        private String lastName;
        private Integer timeInPlatformPerWeek;
        private HashMap<String, Integer> activityScore;
        private HashMap<String, Integer> gameScore;
    }
}
