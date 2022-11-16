package com.example.bmw.domain.category.service;

import com.example.bmw.domain.category.controller.dto.response.CategoryResponse;
import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.category.repository.CategoryRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse save(String name){
        Category category = new Category(name);
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .posts(category.getPosts())
                .name(category.getName())
                .build();
    }

    @Transactional
    public void delete(String name){
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }

    @Transactional
    public CategoryResponse detail(String name){
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponse.builder()
                .posts(category.getPosts())
                .name(category.getName())
                .build();
    }
}
