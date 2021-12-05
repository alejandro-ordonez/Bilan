package org.bilan.co.domain.dtos.forums;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.enums.UserType;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentDto extends AuthorDto{
    private String content;
}
