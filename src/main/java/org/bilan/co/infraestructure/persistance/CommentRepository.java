package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}