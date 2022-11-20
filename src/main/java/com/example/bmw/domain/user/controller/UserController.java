package com.example.bmw.domain.user.controller;

import com.example.bmw.domain.user.controller.dto.request.DeleteUserRequest;
import com.example.bmw.domain.user.controller.dto.request.ProfileUpdateRequest;
import com.example.bmw.domain.user.controller.dto.response.UserResponse;
import com.example.bmw.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public UserResponse profile(){
        return userService.profile();
    }

    @PatchMapping("/mypage")
    public UserResponse profileUpdate(@RequestBody @Valid ProfileUpdateRequest request){
        return userService.profileUpdate(request.getName(), request.getImageUrl());
    }

    @DeleteMapping("/mypage")
    public void deleteUser(@RequestBody @Valid DeleteUserRequest request){
        userService.deleteUser(request);
    }
}
