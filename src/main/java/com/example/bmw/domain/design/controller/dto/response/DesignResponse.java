package com.example.bmw.domain.design.controller.dto.response;

import com.example.bmw.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DesignResponse {
    private Integer id;
    private String designName;
    private String designUrl;
    private User user;
}