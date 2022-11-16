package com.example.bmw.domain.like.controller;

import com.example.bmw.domain.like.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoodController {
    private final GoodService goodService;

    @PostMapping("/template/like/{postId}")
    public void insertGood(@PathVariable int postId){
        goodService.insertGood(postId);
    }

    @DeleteMapping("/template/like/{postId}")
    public void deleteGood(@PathVariable int postId){
        goodService.deleteGood(postId);
    }
}
