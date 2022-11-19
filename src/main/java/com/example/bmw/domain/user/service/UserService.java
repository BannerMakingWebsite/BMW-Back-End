package com.example.bmw.domain.user.service;

import com.example.bmw.domain.user.controller.dto.request.DeleteUserRequest;
import com.example.bmw.domain.user.controller.dto.response.UserResponse;
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
    public UserResponse profile(){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        return UserResponse.builder()
                .id(user.getId())
                .goods(user.getGoods())
                .bookmarks(user.getBookmarks())
                .comments(user.getComments())
                .posts(user.getPosts())
                .email(user.getEmail())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .authority(user.getAuthority())
                .build();
    }

    @Transactional
    public UserResponse profileUpdate(String name, String imageUrl){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        user.profileUpdate(name, imageUrl);
        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .goods(user.getGoods())
                .bookmarks(user.getBookmarks())
                .comments(user.getComments())
                .posts(user.getPosts())
                .email(user.getEmail())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .authority(user.getAuthority())
                .build();
    }

    @Transactional
    public void deleteUser(DeleteUserRequest request){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("password was wrong");

        userRepository.deleteById(user.getId());
    }
}
