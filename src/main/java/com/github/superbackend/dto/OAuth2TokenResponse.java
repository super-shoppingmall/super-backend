package com.github.superbackend.dto;

import lombok.Data;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;


@Data
public class OAuth2TokenResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;
    private String scope;
    public OAuth2TokenResponse(String accessToken, String tokenType, Long expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public OAuth2TokenResponse(OAuth2AccessToken token, OAuth2RefreshToken refreshToken) {
    }
}
