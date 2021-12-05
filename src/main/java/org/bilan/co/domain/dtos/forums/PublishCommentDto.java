package org.bilan.co.domain.dtos.forums;

import lombok.Data;

@Data
public class PublishCommentDto {
    Integer postId;
    String content;
}
