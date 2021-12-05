package org.bilan.co.application.forums;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.forums.CommentDto;
import org.bilan.co.domain.dtos.forums.PublishPostDto;

public interface IForumsService {
    ResponseDto<String> publishPost(PublishPostDto publishPostDto, String jwt);
    ResponseDto<String> publishComment(CommentDto commentDto, String jwt);
}
