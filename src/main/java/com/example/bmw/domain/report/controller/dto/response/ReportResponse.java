package com.example.bmw.domain.report.controller.dto.response;

import com.example.bmw.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private Integer id;
    private Post post;
}
