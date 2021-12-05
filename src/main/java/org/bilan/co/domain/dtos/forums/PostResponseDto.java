package org.bilan.co.domain.dtos.forums;

import lombok.Data;

import java.util.List;

@Data
public class PostResponseDto {
    private List<PostDto> posts;
    private Integer nPages;
}
