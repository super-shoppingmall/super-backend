package com.github.superbackend.config;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import lombok.Data;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Data
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
