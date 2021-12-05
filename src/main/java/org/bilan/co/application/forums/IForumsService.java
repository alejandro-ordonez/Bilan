package org.bilan.co.application.forums;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.forums.PostDto;
import org.bilan.co.domain.dtos.forums.PostResponseDto;
import org.bilan.co.domain.dtos.forums.PublishCommentDto;
import org.bilan.co.domain.dtos.forums.PublishPostDto;

import java.util.List;

public interface IForumsService {
    ResponseDto<String> publishPost(PublishPostDto publishPostDto, String jwt);
    ResponseDto<String> publishComment(PublishCommentDto publishCommentDto, String jwt);

    ResponseDto<PostResponseDto> getPosts(Integer page);
}
