package com.example.bmw.domain.post.service;

import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.category.repository.CategoryRepository;
import com.example.bmw.domain.post.controller.dto.response.PostResponse;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void upload(String design, String title, String name, String preview) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Post post = new Post(title, user, category, design, preview);
        postRepository.save(post);
    }

    @Transactional
    public void delete(int id){
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));
        postRepository.delete(post);
    }

    @Transactional
    public PostResponse detail(int id){
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));
        return PostResponse.builder()
                .id(post.getId())
                .comments(post.getComments())
                .user(post.getUser())
                .category(post.getCategory())
                .design(post.getDesign())
                .title(post.getTitle())
                .goodCount(post.getGoodCount())
                .bookmarkCount(post.getBookmarkCount())
                .createTime(post.getCreateTime())
                .preview(post.getPreview())
                .build();
    }

    @Transactional
    public List<PostResponse> list(){
        List<Post> post = postRepository.findAll();
        return post.stream().map(p -> new PostResponse(
                p.getId(),
                p.getComments(),
                p.getUser(),
                p.getCategory(),
                p.getDesign(),
                p.getTitle(),
                p.getGoodCount(),
                p.getBookmarkCount(),
                p.getCreateTime(),
                p.getPreview())).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> search(String keyword){
        List<Post> post = postRepository.findByTitleContaining(keyword);
        return post.stream().map(p -> new PostResponse(
                p.getId(),
                p.getComments(),
                p.getUser(),
                p.getCategory(),
                p.getDesign(),
                p.getTitle(),
                p.getGoodCount(),
                p.getBookmarkCount(),
                p.getCreateTime(),
                p.getPreview())).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> popularList(){
        List<Post> post = postRepository.findAll(Sort.by(Sort.Direction.DESC, "goodCount"));
        return post.stream().map(p -> new PostResponse(
                p.getId(),
                p.getComments(),
                p.getUser(),
                p.getCategory(),
                p.getDesign(),
                p.getTitle(),
                p.getGoodCount(),
                p.getBookmarkCount(),
                p.getCreateTime(),
                p.getPreview())).collect(Collectors.toList());
    }
}
