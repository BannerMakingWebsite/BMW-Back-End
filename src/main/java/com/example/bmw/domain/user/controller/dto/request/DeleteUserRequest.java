package com.example.bmw.domain.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequest {
    @NotEmpty(message = "비밀번호는 필수입력란입니다.")
    private String password;
}
