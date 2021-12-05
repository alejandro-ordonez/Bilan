package org.bilan.co.domain.dtos.forums;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.enums.UserType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostDto extends CommentDto{
    private String title;
}
