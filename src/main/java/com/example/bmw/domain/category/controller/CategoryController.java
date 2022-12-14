package com.example.bmw.domain.category.controller;

import com.example.bmw.domain.category.controller.dto.request.CategoryRequest;
import com.example.bmw.domain.category.controller.dto.response.CategoryResponse;
import com.example.bmw.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/template/category")
    public CategoryResponse save(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest.getCategoryName());
    }

    @DeleteMapping("/template/category")
    public void delete(@RequestBody @Valid CategoryRequest categoryRequest){
        categoryService.delete(categoryRequest.getCategoryName());
    }

    @GetMapping("/template/category")
    public CategoryResponse detail(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.detail(categoryRequest.getCategoryName());
    }
}
