package com.example.bmw.global.auth;

import com.example.bmw.domain.user.entity.Authority;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDetailService extends DefaultOAuth2UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(AuthDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND_USER"));
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // DefaultOAuth2UserService: 객체를 성공정보를 바탕으로 만든다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        // 생성된 Service 객체로 부터 User를 받는다.
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // 받은 User로 부터 user 정보를 받는다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        if(!userRepository.existsByEmail(oAuth2User.getAttribute("email"))){
            userRepository.save(User.builder()
                    .email(oAuth2User.getAttribute("email"))
                    .name(oAuth2User.getAttribute("name"))
                    .imageUrl(oAuth2User.getAttribute("picture"))
                    .authority(Authority.USER)
                    .build());
        }else{
            User user = userRepository.findByEmail(oAuth2User.getAttribute("email")).orElseThrow(()-> new IllegalArgumentException("NOT_FOUND"));
            user.oauthUpdate(oAuth2User.getAttribute("name"), oAuth2User.getAttribute("picture"));
            userRepository.save(user);
        }

        return super.loadUser(userRequest);
    }
}
