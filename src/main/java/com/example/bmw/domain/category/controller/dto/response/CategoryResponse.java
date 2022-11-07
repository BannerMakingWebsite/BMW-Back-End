package com.example.bmw.domain.category.controller.dto.response;

import com.example.bmw.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private List<Post> posts;
    private String name;
}
