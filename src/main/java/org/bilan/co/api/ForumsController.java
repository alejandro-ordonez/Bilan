package org.bilan.co.api;

import org.bilan.co.application.forums.IForumsService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.forums.PostResponseDto;
import org.bilan.co.domain.dtos.forums.PublishCommentDto;
import org.bilan.co.domain.dtos.forums.PostDto;
import org.bilan.co.domain.dtos.forums.PublishPostDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forums")
public class ForumsController {

    @Autowired
    private IForumsService forumService;

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @PostMapping("/post")
    public ResponseEntity<ResponseDto<String>> publishPost(@RequestBody PublishPostDto publishPostDto,
                                                           @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        ResponseDto<String> result = forumService.publishPost(publishPostDto, jwt);
        return ResponseEntity.status(result.getCode()).body(result);
    }


    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @PostMapping("/comment")
    public ResponseEntity<ResponseDto<String>> publishComment(@RequestBody PublishCommentDto publishCommentDto,
                                                              @RequestHeader(Constants.AUTHORIZATION) String jwt){
        ResponseDto<String> result = forumService.publishComment(publishCommentDto, jwt);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping("/threads")
    public ResponseEntity<ResponseDto<PostResponseDto>> getThreads(@RequestParam("page") Integer page){

        ResponseDto<PostResponseDto> posts = forumService.getPosts(page);
        return ResponseEntity.status(posts.getCode()).body(posts);
    }


}
