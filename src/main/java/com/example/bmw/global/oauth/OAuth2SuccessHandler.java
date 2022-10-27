package com.example.bmw.global.oauth;

import com.example.bmw.domain.user.controller.dto.UserDto;
import com.example.bmw.domain.user.controller.dto.response.TokenResponse;
import com.example.bmw.domain.user.entity.Authority;
import com.example.bmw.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserRequestMapper userRequestMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

        log.info("Principal에서 꺼낸 oauth2User : {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.

        log.info("토큰 발행 시작");

        TokenResponse token = new TokenResponse(
                tokenProvider.createAccessToken(userDto.getEmail(), Authority.USER), tokenProvider.createRefreshToken());
        log.info("AccessToken : {}", token.getAccessToken());
        log.info("RefreshToken : {}", token.getRefreshToken());
        log.info("Token : {}", token);

        String url = makeRedirectUrl(token.getAccessToken(), token.getRefreshToken());
        log.info("url : {}", url);

        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String accessToken, String refreshToken){
        return UriComponentsBuilder.fromUriString("http://localhost:8080/oauth2/redirect/" + "accessToken=" + accessToken + "&" + "refreshToken=" + refreshToken)
                .build().toUriString();

    }
}