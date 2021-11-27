package org.bilan.co.domain.dtos.user;

import lombok.Data;
import org.bilan.co.domain.dtos.BasicInfo;
import org.bilan.co.domain.dtos.college.ClassRoomDto;

import java.util.List;

@Data
public class EnrollmentDto extends BasicInfo {
    private List<ClassRoomDto> coursesToEnroll;
}
