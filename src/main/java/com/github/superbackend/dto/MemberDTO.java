package com.github.superbackend.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class MemberDTO {
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gender;
    private String aboutMe;
    private String profileImage;

    // Getter, Setter 및 생성자 생략

    public boolean isPasswordValid() {
        // 비밀번호 검증 로직 추가
        if (password == null || password.isEmpty()) {
            return false;
        }

        // 비밀번호는 영문자와 숫자의 조합으로 8자 이상 20자 이하여야 합니다.
        return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$");
    }
}
