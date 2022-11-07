package com.example.bmw.domain.comment.controller.dto.response;

import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Integer id;
    private User user;
    private Post post;
    private String comment;
}
