package com.example.bmw.domain.post.controller;

import com.example.bmw.domain.post.controller.dto.request.UploadRequest;
import com.example.bmw.domain.post.controller.dto.response.PostResponse;
import com.example.bmw.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/template")
    public PostResponse upload(@RequestBody UploadRequest upload) {
        return postService.upload(upload.getDesignName(), upload.getTitle(), upload.getName());
    }

    @DeleteMapping("/template/{id}")
    public void delete(@PathVariable int id){
        postService.delete(id);
    }

    @GetMapping("/template/{id}")
    public PostResponse detail(@PathVariable int id){
        return postService.detail(id);
    }

    @GetMapping("/template/list")
    public List<PostResponse> list(){
        return postService.list();
    }

    @GetMapping("/template")
    public List<PostResponse> search(@RequestParam String keyword){
        return postService.search(keyword);
    }

    @GetMapping("/template/popular")
    public List<PostResponse> popularList(){
        return postService.popularList();
    }
}
