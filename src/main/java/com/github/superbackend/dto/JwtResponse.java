package com.github.superbackend.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
    }

    // Getter 및 Setter 메서드
    // 필요한 경우 추가 정보도 포함할 수 있습니다.
}