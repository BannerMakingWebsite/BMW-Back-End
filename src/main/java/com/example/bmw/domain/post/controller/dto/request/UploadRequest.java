package com.example.bmw.domain.post.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequest {
    @NotEmpty(message = "제목은 필수입력란 입니다.")
    private String title;
    @NotEmpty(message = "카테고리 이름은 필수 입력란 입니다.")
    private String categoryName;
    @NotEmpty(message = "디자인은 필수 입력란 입니다.")
    private String design;
    private String preview;
}
