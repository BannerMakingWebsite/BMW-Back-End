package com.example.bmw.domain.post.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequest {
    private String title;
    private String name;
    private MultipartFile file;
}
