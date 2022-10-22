package com.example.bmw.global.oauth.user;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
        return new GoogleOAuth2UserInfo(attributes);
    }
}
