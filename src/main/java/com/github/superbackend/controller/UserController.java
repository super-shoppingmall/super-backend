package com.github.superbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasRole('USER')") // 해당 메소드에 접근하는 사용자는 "USER" 권한을 가져야 함
    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo() {
        // 사용자 정보를 반환하는 코드
        String userInfo = "User Information: ..."; // 실제 정보를 여기에 채워넣으세요.
        return ResponseEntity.ok(userInfo);
    }
}