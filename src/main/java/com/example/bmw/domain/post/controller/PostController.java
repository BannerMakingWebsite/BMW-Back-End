package com.example.bmw.domain.post.controller;

import com.example.bmw.domain.post.controller.dto.request.UploadRequest;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/template")
    public Post upload(@RequestPart MultipartFile multipartFile, @RequestBody UploadRequest upload) throws Exception{
        return postService.upload(multipartFile, upload.getTitle(), upload.getName());
    }

    @DeleteMapping("/template/{id}")
    public void delete(@PathVariable int id){
        postService.delete(id);
    }

    @GetMapping("/template/{id}")
    public Post detail(@PathVariable int id){
        return postService.detail(id);
    }

    @GetMapping("/template/list")
    public List<Post> list(){
        return postService.list();
    }

    @GetMapping("/template")
    public List<Post> search(@RequestParam String keyword){
        return postService.search(keyword);
    }

    @GetMapping("/template/popular")
    public List<Post> popularList(){
        return postService.popularList();
    }
}
