package com.github.superbackend.controller;

import com.github.superbackend.dto.OAuth2TokenResponse;
import com.github.superbackend.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {


    private CustomOAuth2UserService customOAuth2UserService;

    @PostMapping("/exchange-token")
    public ResponseEntity<?> exchangeToken(@RequestParam("code") String code, @RequestParam("state") String state) {
        // "code"와 "state"를 사용하여 OAuth 2.0 토큰을 얻는 로직을 구현
        OAuth2TokenResponse tokenResponse = customOAuth2UserService.exchangeToken(code, state);

        if (tokenResponse != null) {
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token exchange failed");
        }
    }
}