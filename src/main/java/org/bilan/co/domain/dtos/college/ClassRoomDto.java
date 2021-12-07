package org.bilan.co.domain.dtos.college;

import lombok.Data;

@Data
public class ClassRoomDto {
    private String collegeName;
    private Integer classroomId;
    private String grade;
    private Integer courseId;
    private Integer tribeId;
    private Integer collegeId;
}
