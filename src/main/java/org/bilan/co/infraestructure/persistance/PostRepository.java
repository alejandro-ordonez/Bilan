package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT post FROM Post post ORDER BY post.createdAt DESC")
    Page<Post> getPosts(Pageable page);

}