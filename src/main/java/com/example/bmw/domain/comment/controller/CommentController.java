package com.example.bmw.domain.comment.controller;

import com.example.bmw.domain.comment.controller.dto.request.CommentRequest;
import com.example.bmw.domain.comment.entity.Comment;
import com.example.bmw.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/template/comment/{id}")
    public void save(@PathVariable int id, @RequestBody CommentRequest request){
        commentService.save(request, id);
    }

    @DeleteMapping("/template/comment/{id}")
    public void delete(@PathVariable int id){
        commentService.delete(id);
    }
}
