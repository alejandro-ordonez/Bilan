package org.bilan.co.domain.dtos.student;

import lombok.Data;

@Data
public class StudentDashboardDto extends StudentStatsRecord{
    private float timeInApp;
    private float progressActivities;
}
