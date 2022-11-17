package com.example.bmw.domain.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {
    @NotEmpty(message = "이름은 필수 입력란입니다.")
    private String name;
    private String imageUrl;
}
