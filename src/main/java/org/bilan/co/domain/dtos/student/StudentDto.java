package org.bilan.co.domain.dtos.student;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.user.UserInfoDto;

@Data
@NoArgsConstructor
public class StudentDto extends UserInfoDto {
    String grade;
    String course;
}
