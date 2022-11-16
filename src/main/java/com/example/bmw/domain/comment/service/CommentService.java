package com.example.bmw.domain.comment.service;

import com.example.bmw.domain.comment.controller.dto.request.CommentRequest;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.comment.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(CommentRequest commentRequest, int id){
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

        Comment comment = new Comment(user, post, commentRequest.getComment());
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(int id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
