package com.github.superbackend.dto;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

    // Getter와 Setter 메서드 추가
}
