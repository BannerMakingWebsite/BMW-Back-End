package com.example.bmw.domain.like.repository;

import com.example.bmw.domain.like.entity.Good;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Integer> {
    Optional<Good> findByUserAndPost(User user, Post post);
}
