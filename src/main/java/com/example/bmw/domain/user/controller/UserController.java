package com.example.bmw.domain.user.controller;

import com.example.bmw.domain.user.controller.dto.request.DeleteUserRequest;
import com.example.bmw.domain.user.controller.dto.request.ProfileUpdateRequest;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public User profile(){
        return userService.profile();
    }

    @PatchMapping("/mypage")
    public User profileUpdate(@RequestBody ProfileUpdateRequest request){
        return userService.profileUpdate(request.getName());
    }

    @DeleteMapping("/mypage")
    public void deleteUser(@RequestBody DeleteUserRequest request){
        userService.deleteUser(request);
    }
}
