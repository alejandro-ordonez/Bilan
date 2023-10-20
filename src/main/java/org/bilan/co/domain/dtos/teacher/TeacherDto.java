package org.bilan.co.domain.dtos.teacher;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.college.ClassRoomDto;
import org.bilan.co.domain.dtos.user.UserInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
public class TeacherDto extends UserInfoDto {

    private String codDaneMinResidencia;

    private String codDane;

    private String codDaneSede;

    private List<ClassRoomDto> classRoomDtoList;
}
