package com.example.bmw.global.jwt;

import com.example.bmw.domain.user.controller.dto.UserDto;
import com.example.bmw.domain.user.entity.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.resolveAccessToken(request);
        log.info(token);
        log.info("토큰을 확인하는중");
        log.info(request.getRequestURI());

        if (token != null && tokenProvider.validateAccessToken(token)) {
            log.info("정보 입력");

            String email = tokenProvider.getUserEmail(token);
            UserDto userDto = UserDto.builder()
                    .email(email)
                    .name("이름이에용").build();

            Authentication auth = getAuthentication(userDto);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }

    public Authentication getAuthentication(UserDto user) {
        return new UsernamePasswordAuthenticationToken(user, "",
                List.of(new SimpleGrantedAuthority(Authority.USER.name())));
    }
}
