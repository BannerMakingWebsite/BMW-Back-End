package com.example.bmw.domain.bookmark.repository;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    Optional<Bookmark> findByUserAndPost(User user, Post post);
    Optional<List<Bookmark>> findByUser(User user);
}
