package com.example.bmw.domain.post.entity;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.like.entity.Good;
import com.example.bmw.domain.report.entity.Report;
import com.example.bmw.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Good> goods;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String title;

    @Column
    private String s3FileName;

    @Column
    private String s3FileUrl;

    @Column
    private int goodCount;

    @Column
    private int bookmarkCount;

    @Column
    private LocalDateTime createTime;

    public Post(String s3FileName, String s3FileUrl, String title, User user, Category category){
        this.user = user;
        this.s3FileUrl = s3FileUrl;
        this.s3FileName = s3FileName;
        this.goodCount = 0;
        this.bookmarkCount = 0;
        this.createTime = LocalDateTime.now();
        this.title = title;
        this.category = category;
    }

    public void setGoodCount(int goodCount){
        this.goodCount = goodCount;
    }
    public void setBookmarkCount(int bookmarkCount){this.bookmarkCount = bookmarkCount;}
}
