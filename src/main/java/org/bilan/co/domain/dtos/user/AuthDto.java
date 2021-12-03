package org.bilan.co.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDto extends AuthenticatedUserDto {

    private String password;
    private String email;

    //Student params
    private String grade;
    protected Integer courseId;
    private Integer collegeId;

    public AuthDto(String document, DocumentType documentType, UserType userType, String password) {
        super(document, userType, documentType);
        this.password = password;
    }
}
