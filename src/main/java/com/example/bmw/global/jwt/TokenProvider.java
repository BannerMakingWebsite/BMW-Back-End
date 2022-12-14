package com.example.bmw.global.jwt;

import com.example.bmw.domain.user.controller.dto.UserDto;
import com.example.bmw.domain.user.entity.Authority;
import com.example.bmw.global.redis.RedisDao;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    private long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; //1시간

    private long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7; //일주일
    public static final String HEADER_REFRESH_TOKEN = "RefreshToken";
    private final RedisDao redisDao;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String email, Authority role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(email);
        String refresh =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        redisDao.setValues(email, refresh, Duration.ofMillis(REFRESH_TOKEN_VALID_TIME));
        return refresh;
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader(HEADER_REFRESH_TOKEN);
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}
