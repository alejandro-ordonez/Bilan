package org.bilan.co.infraestructure.persistance;

import org.bilan.co.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}