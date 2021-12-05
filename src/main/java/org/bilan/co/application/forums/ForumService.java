package org.bilan.co.application.forums;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.forums.*;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.Comment;
import org.bilan.co.domain.entities.Post;
import org.bilan.co.domain.entities.UserInfo;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.infraestructure.persistance.CommentRepository;
import org.bilan.co.infraestructure.persistance.PostRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseDto<String> publishComment(PublishCommentDto publishCommentDto, String jwt) {

        UserInfo userInfo = getAuthor(jwt);

        Comment comment = new Comment();
        comment.setAuthor(userInfo);
        comment.setContent(publishCommentDto.getContent());

        Post post = new Post();
        post.setId(publishCommentDto.getPostId());

        comment.setPost(post);

        commentRepository.save(comment);

        return new ResponseDto<>("Comment added successfully", 200, "");
    }

    @Override
    public ResponseDto<PagedResponse<PostDto>> getPosts(Integer page) {

        Objects.requireNonNull(page);

        Page<Post> posts = postRepository.getPosts(PageRequest.of(page, 10));

        List<PostDto> postsDto = posts.map(this::getPostDto)
                .stream()
                .collect(Collectors.toList());

        PagedResponse<PostDto> postResponseDto = new PagedResponse<>();
        postResponseDto.setNPages(posts.getTotalPages());
        postResponseDto.setData(postsDto);

        return new ResponseDto<>("Posts retrieved, page "+page, 200, postResponseDto);
    }

    @Override
    public ResponseDto<PagedResponse<CommentDto>> getComments(Integer postId, Integer page) {

        Objects.requireNonNull(postId);
        Objects.requireNonNull(page);

        Page<Comment> comments = commentRepository.getCommentsByPostId(PageRequest.of(page, 10) ,postId);

        PagedResponse<CommentDto> response = new PagedResponse<>();
        response.setNPages(comments.getTotalPages());

        List<CommentDto> commentsDto = comments
                .stream().map(this::getCommentDto)
                .collect(Collectors.toList());

        response.setData(commentsDto);

        return new ResponseDto<>("Comments retrieved successfully", 200, response);
    }

    private PostDto getPostDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setAuthor(post.getAuthor().getName() + " " + post.getAuthor().getLastName());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setId(post.getId());
        postDto.setUserType(getUserType(post.getAuthor().getRole().getId()));

        return postDto;
    }


    private CommentDto getCommentDto(Comment c) {
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthor(c.getAuthor().getName() + " " + c.getAuthor().getLastName());
        commentDto.setContent(c.getContent());
        commentDto.setCreatedAt(c.getCreatedAt());
        commentDto.setId(c.getId());

        commentDto.setUserType(getUserType(c.getAuthor().getRole().getId()));

        return commentDto;
    }

    private UserType getUserType(Integer id) {
        switch (id){
            case 1:
                return UserType.Student;
            case 2:
                return UserType.Teacher;
            default:
                return UserType.Unknown;
        }
    }

    private UserInfo getAuthor(String jwt){
        AuthenticatedUserDto authenticatedUserDto =jwtTokenUtil.getInfoFromToken(jwt);
        UserInfo user = new UserInfo();
        user.setDocument(authenticatedUserDto.getDocument());
        return user;
    }
}
