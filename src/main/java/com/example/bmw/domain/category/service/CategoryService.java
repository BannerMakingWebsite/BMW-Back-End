package com.example.bmw.domain.category.service;

import com.example.bmw.domain.category.entity.Category;
import com.example.bmw.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category save(String name){
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(String name){
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("not found"));
        categoryRepository.delete(category);
    }

    @Transactional
    public Category detail(String name){
        return categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
