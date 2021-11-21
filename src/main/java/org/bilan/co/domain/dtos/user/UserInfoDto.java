package org.bilan.co.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDto extends AuthenticatedUserDto{

    private String name;
    private String lastName;
    private String email;
}
