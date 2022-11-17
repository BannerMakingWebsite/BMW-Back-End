package com.example.bmw.domain.user.controller;

import com.example.bmw.domain.user.controller.dto.request.*;
import com.example.bmw.domain.user.controller.dto.response.TokenResponse;
import com.example.bmw.domain.user.controller.dto.response.UserResponse;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody @Valid SignupRequest request){
        return authService.signup(request);
    }

    @PostMapping("/bmw")
    public TokenResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }

    @PutMapping("/newAccess")
    public TokenResponse issueAccessToken(HttpServletRequest request){
        return authService.issueAccessToken(request);
    }

    @PostMapping("/send")
    public void send(@RequestBody @Valid EmailRequest request){
        authService.send(request.getEmail());
    }

    @GetMapping("/signUpConfirm")
    public void verify(@RequestParam String email, @RequestParam String authKey){
        authService.verify(email, authKey);
    }

    @GetMapping("/passwordConfirm")
    public void passwordVerify(@RequestParam String email, @RequestParam String authKey){
        authService.passwordVerify(email, authKey);
    }

    @PostMapping("/passwordReset")
    public void passwordReset(@RequestBody @Valid PasswordRequest request){
        authService.changePassword(request);
    }
}
