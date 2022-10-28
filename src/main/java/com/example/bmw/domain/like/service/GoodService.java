package com.example.bmw.domain.like.service;

import com.example.bmw.domain.like.entity.Good;
import com.example.bmw.domain.like.repository.GoodRepository;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GoodRepository goodRepository;

    private boolean isNotAlreadyGood(Post post, User user){
        return goodRepository.findByUserAndPost(user, post).isEmpty();
    }

    @Transactional
    public void insertGood(int postId) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Good like = new Good(post, user);

        if(isNotAlreadyGood(post, user)){
            goodRepository.save(like);
            post.setGoodCount(post.getGoodCount() + 1);
            postRepository.save(post);
        }
        else{
            throw new RuntimeException();
        }
    }

    @Transactional
    public void deleteGood(int postId) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Good good = goodRepository.findByUserAndPost(user, post).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        goodRepository.delete(good);

        post.setGoodCount(post.getGoodCount() - 1);
        postRepository.save(post);
    }
}
