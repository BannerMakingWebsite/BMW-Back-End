package com.example.bmw.domain.post.repository;

import com.example.bmw.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByTitleContaining(String keyword);
}
