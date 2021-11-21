package org.bilan.co.domain.dtos.college;

import lombok.Data;
import org.bilan.co.domain.dtos.student.StudentStatsRecord;

import java.util.List;

@Data
public class ClassRoomStats {
    private Float groupProgress;
    private Integer students;

    private List<StudentStatsRecord> studentStatsRecords;
}
