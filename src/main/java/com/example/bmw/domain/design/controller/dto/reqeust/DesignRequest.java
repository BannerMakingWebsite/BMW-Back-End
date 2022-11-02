package com.example.bmw.domain.design.controller.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DesignRequest {
    private String designName;
    private MultipartFile file;
}
