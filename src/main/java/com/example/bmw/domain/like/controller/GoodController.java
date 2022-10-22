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

    @PostMapping("/template/like/{id}")
    public void insertGood(@PathVariable int id){
        goodService.insertGood(id);
    }

    @DeleteMapping("/template/like/{id}")
    public void deleteGood(@PathVariable int id){
        goodService.deleteGood(id);
    }
}
