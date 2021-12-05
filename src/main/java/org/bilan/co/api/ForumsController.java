package org.bilan.co.api;

import org.bilan.co.application.forums.IForumsService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.forums.CommentDto;
import org.bilan.co.domain.dtos.forums.PostDto;
import org.bilan.co.domain.dtos.forums.PublishPostDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forums")
public class ForumsController {

    @Autowired
    private IForumsService forumService;

    @PostMapping("/post")
    public ResponseEntity<ResponseDto<String>> publishPost(@RequestBody PublishPostDto publishPostDto,
                                                           @RequestHeader(Constants.AUTHORIZATION) String jwt){
        ResponseDto<String> result = forumService.publishPost(publishPostDto, jwt);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseDto<String>> publishComment(@RequestBody CommentDto commentDto,
                                                              @RequestHeader(Constants.AUTHORIZATION) String jwt){
        ResponseDto<String> result = forumService.publishComment(commentDto, jwt);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @GetMapping("/threads")
    public ResponseEntity<ResponseDto<List<PostDto>>> getThreads(){
        return null;
    }


}
