package com.example.bmw.domain.category.controller;

import com.example.bmw.domain.category.controller.dto.request.CategoryRequest;
import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/template/category")
    public Category save(@RequestBody CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest.getName());
    }

    @DeleteMapping("/template/category/{id}")
    public void delete(@PathVariable int id){
        categoryService.delete(id);
    }

    @GetMapping("/template/category/{id}")
    public Category detail(@PathVariable int id){
        return categoryService.detail(id);
    }
}
