package org.bilan.co.application.forums;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.forums.CommentDto;
import org.bilan.co.domain.dtos.forums.PostDto;
import org.bilan.co.domain.dtos.forums.PublishCommentDto;
import org.bilan.co.domain.dtos.forums.PublishPostDto;

import java.util.List;

public interface IForumsService {
    ResponseDto<String> publishPost(PublishPostDto publishPostDto, String jwt);
    ResponseDto<String> publishComment(PublishCommentDto publishCommentDto, String jwt);

    ResponseDto<PagedResponse<PostDto>> getPosts(Integer page);

    ResponseDto<PagedResponse<CommentDto>> getComments(Integer postId, Integer page);
}
