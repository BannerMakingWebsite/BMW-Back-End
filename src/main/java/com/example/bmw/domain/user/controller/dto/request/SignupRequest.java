package com.example.bmw.domain.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String name;
}
