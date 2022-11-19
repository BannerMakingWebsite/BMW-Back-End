package com.example.bmw.domain.post.controller;

import com.example.bmw.domain.post.controller.dto.request.UploadRequest;
import com.example.bmw.domain.post.controller.dto.response.PostResponse;
import com.example.bmw.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/template")
    public void upload(@RequestBody @Valid UploadRequest upload) {
        postService.upload(upload.getDesign(), upload.getTitle(), upload.getCategoryName(), upload.getPreview());
    }

    @DeleteMapping("/template/{postId}")
    public void delete(@PathVariable int postId){
        postService.delete(postId);
    }

    @GetMapping("/template/{[postId}")
    public PostResponse detail(@PathVariable int postId){
        return postService.detail(postId);
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
