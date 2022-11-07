package com.example.bmw.domain.user.entity;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.design.entity.Design;
import com.example.bmw.domain.like.entity.Good;
import com.example.bmw.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Design> designs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Good> goods;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String imageUrl;

    @Column
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private boolean isVerify;
    private boolean passwordVerify;

    private String authKey;

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void verify(){
        this.isVerify = true;
    }
    public void setPasswordVerify(boolean verify){this.passwordVerify = verify;}
    public void setPassword(String password){
        this.password = password;
    }

    public void oauthUpdate(String name, String imageUrl){
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void signup(String password, String name) {
        this.password = password;
        this.name = name;
        this.authority = Authority.USER;
        this.isVerify = false;
    }

    public void profileUpdate(String name){
        this.name = name;
    }
}
