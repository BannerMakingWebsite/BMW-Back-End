package com.example.bmw.domain.category.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty(message = "카테고리 이름은 필수 입력입니다.")
    private String categoryName;
}
