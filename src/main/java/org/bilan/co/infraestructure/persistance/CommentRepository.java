package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT comment FROM Comment comment WHERE comment.post.id = :postId ORDER BY comment.createdAt DESC")
    Page<Comment> getCommentsByPostId(Pageable pageable, Integer postId);
}