package com.github.superbackend.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;

    // 생성자, Getter 및 Setter는 Lombok이 자동으로 생성합니다.
}
