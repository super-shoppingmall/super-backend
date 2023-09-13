package com.github.superbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "profile_image")
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "status")
    private String status;


    private int loginAttempts; // 로그인 실패 횟수
    private Date lockTime;     // 잠금 해제 시간

    private static final int  maxLoginAttempts = 5;   // 최대 로그인 실패 횟수
    // 실패 횟수 증가 메서드
    public void incrementLoginAttempts() {
        loginAttempts++;
    }

    // 잠금 상태 설정 메서드
    // 잠금 상태 설정 메서드
    public void setAccountLocked(int maxAttempts, int lockDurationMinutes) {
        loginAttempts =  maxLoginAttempts ;
        lockTime = new Date(System.currentTimeMillis() + lockDurationMinutes * 60 * 1000); // 현재 시간 + 잠금 기간(분)
    }
    // 로그인 시 잠긴 상태인지 확인하는 메서드
    public boolean isAccountLocked() {
        return loginAttempts >=maxLoginAttempts && lockTime != null && lockTime.after(new Date());
    }
    public UserDetails toUserDetails() {
        // 사용자의 권한을 설정하고 UserDetails를 생성합니다.
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(email, password, authorities);
    }

}
//userdetails
