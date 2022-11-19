package com.example.bmw.domain.post.controller.dto.response;

import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Integer id;
    private List<Comment> comments;
    private User user;
    private Category category;
    private String design;
    private String title;
    private int goodCount;
    private int bookmarkCount;
    private LocalDateTime createTime;
    private String preview;
}
