package org.bilan.co.domain.dtos.forums;

import lombok.Data;
import org.bilan.co.domain.dtos.user.UserInfoDto;

@Data
public class PostDto extends PublishPostDto{

    private UserInfoDto author;

}
