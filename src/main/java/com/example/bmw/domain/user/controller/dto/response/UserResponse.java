package com.example.bmw.domain.user.controller.dto.response;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.design.entity.Design;
import com.example.bmw.domain.like.entity.Good;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.user.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private List<Design> designs;
    private List<Good> goods;
    private List<Bookmark> bookmarks;
    private List<Comment> comments;
    private List<Post> posts;
    private String email;
    private String name;
    private String imageUrl;
    private Authority authority;
}
