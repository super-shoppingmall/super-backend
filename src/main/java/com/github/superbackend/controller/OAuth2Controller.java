package com.github.superbackend.controller;

import com.github.superbackend.dto.OAuth2TokenResponse;
import com.github.superbackend.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {


//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @PostMapping("/exchange-kakao-token")
//    public ResponseEntity<?> exchangeKakaoToken(@RequestParam("code") String code, @RequestParam("state") String state) {
//        // 카카오 토큰 교환
//        OAuth2TokenResponse tokenResponse = customOAuth2UserService.exchangeKakaoToken(code, state);
//
//        if (tokenResponse != null) {
//            return ResponseEntity.ok(tokenResponse);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kakao token exchange failed");
//        }
//    }
//
//    @PostMapping("/exchange-naver-token")
//    public ResponseEntity<?> exchangeNaverToken(@RequestParam("code") String code, @RequestParam("state") String state) {
//        // 네이버 토큰 교환
//        OAuth2TokenResponse tokenResponse = customOAuth2UserService.exchangeNaverToken(code, state);
//
//        if (tokenResponse != null) {
//            return ResponseEntity.ok(tokenResponse);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Naver token exchange failed");
//        }
//    }
}