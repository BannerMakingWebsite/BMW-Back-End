package com.example.bmw.domain.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;
    private String newPassword;
}
