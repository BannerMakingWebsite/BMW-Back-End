package com.example.bmw.domain.user.service;

import com.example.bmw.domain.user.controller.dto.request.DeleteUserRequest;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User profile(){
        return userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Transactional
    public User profileUpdate(String name){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        user.profileUpdate(name);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(DeleteUserRequest request){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("password was wrong");

        userRepository.deleteById(user.getId());
    }
}
