package com.example.bmw.domain.comment.controller;

import com.example.bmw.domain.comment.controller.dto.request.CommentRequest;
import com.example.bmw.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/template/comment/{postId}")
    public void save(@PathVariable int postId, @RequestBody CommentRequest request){
        commentService.save(request, postId);
    }

    @DeleteMapping("/template/comment/{commentId}")
    public void delete(@PathVariable int commentId){
        commentService.delete(commentId);
    }
}
