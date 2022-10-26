package com.example.bmw.global.config;

import com.example.bmw.domain.user.entity.Authority;
import com.example.bmw.global.jwt.JwtFilter;
import com.example.bmw.global.jwt.TokenProvider;
import com.example.bmw.global.oauth.CustomOAuth2UserService;
import com.example.bmw.global.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.bmw.global.oauth.OAuth2AuthenticationFailureHandler;
import com.example.bmw.global.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/bmw",
                        "/login",
                        "/signup",
                        "/send",
                        "/password",
                        "/signUpConfirm",
                        "/passwordConfirm",
                        "/passwordReset")
                .permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/**", "/oauth2/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);


        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
