package org.bilan.co.domain.dtos.forums;

import lombok.Data;
import org.bilan.co.domain.enums.UserType;

@Data
public class AuthorDto {
    private String author;
    private UserType userType;
}
