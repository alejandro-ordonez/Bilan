package org.bilan.co.domain.dtos.forums;

import lombok.Data;

@Data
public class CommentDto {
    Integer postId;
    String content;
}
