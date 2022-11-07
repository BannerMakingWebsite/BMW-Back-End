package com.example.bmw.domain.post.controller.dto.response;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.design.entity.Design;
import com.example.bmw.domain.like.entity.Good;
import com.example.bmw.domain.report.entity.Report;
import com.example.bmw.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Integer id;
    private List<Good> goods;
    private List<Bookmark> bookmarks;
    private List<Report> reports;
    private List<Comment> comments;
    private User user;
    private Category category;
    private Design design;
    private String title;
    private int goodCount;
    private int bookmarkCount;
    private LocalDateTime createTime;
}