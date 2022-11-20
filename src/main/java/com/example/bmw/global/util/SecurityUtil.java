package com.example.bmw.global.util;

import com.example.bmw.domain.user.controller.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityUtil {
    public static String getLoginUserEmail(){
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = (UserDto)user;
        log.info(userDto.getEmail());
        return userDto.getEmail();
    }
}
