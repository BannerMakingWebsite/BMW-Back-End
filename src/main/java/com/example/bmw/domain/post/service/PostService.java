package com.example.bmw.domain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.category.repository.CategoryRepository;
import com.example.bmw.domain.design.entity.Design;
import com.example.bmw.domain.design.repository.DesignRepository;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DesignRepository designRepository;

    @Transactional
    public Post upload(String designName, String title, String name) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Design design = designRepository.findByDesignName(designName).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Post saveImage = new Post(title, user, category, design);
        return postRepository.save(saveImage);
    }

    @Transactional
    public void delete(int id){
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        postRepository.delete(post);
    }

    @Transactional
    public Post detail(int id){
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Transactional
    public List<Post> list(){
        return postRepository.findAll();
    }

    @Transactional
    public List<Post> search(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }

    @Transactional
    public List<Post> popularList(){
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "goodCount"));
    }
}
