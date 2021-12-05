package org.bilan.co.application.forums;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.forums.CommentDto;
import org.bilan.co.domain.dtos.forums.PublishPostDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.Comment;
import org.bilan.co.domain.entities.Post;
import org.bilan.co.domain.entities.UserInfo;
import org.bilan.co.infraestructure.persistance.CommentRepository;
import org.bilan.co.infraestructure.persistance.PostRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForumService implements IForumsService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseDto<String> publishPost(PublishPostDto publishPostDto, String jwt) {

        UserInfo userInfo = getAuthor(jwt);

        Post post = new Post();
        post.setAuthor(userInfo);
        post.setContent(publishPostDto.getContent());
        post.setTitle(publishPostDto.getTitle());

        postRepository.save(post);

        return new ResponseDto<>("Post successfully saved", 200, "");
    }

    @Override
    public ResponseDto<String> publishComment(CommentDto commentDto, String jwt) {

        UserInfo userInfo = getAuthor(jwt);

        Comment comment = new Comment();
        comment.setAuthor(userInfo);
        comment.setContent(commentDto.getContent());

        Post post = new Post();
        post.setId(commentDto.getPostId());

        commentRepository.save(comment);

        return new ResponseDto<>("Comment added successfully", 200, "");
    }

    private UserInfo getAuthor(String jwt){
        AuthenticatedUserDto authenticatedUserDto =jwtTokenUtil.getInfoFromToken(jwt);
        UserInfo user = new UserInfo();
        user.setDocument(authenticatedUserDto.getDocument());
        return user;
    }
}
