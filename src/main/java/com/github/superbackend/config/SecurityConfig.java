package com.github.superbackend.config;

import com.github.superbackend.security.JwtAuthenticationFilter;
import com.github.superbackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtUtil jwtUtil;
    private MemberService memberService;

    @Autowired
    public SecurityConfig(JwtUtil jwtUtil, MemberService memberService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter1() {
        return new JwtAuthenticationFilter();
    }
    @Bean // AuthenticationManager 빈 등록
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/login/oauth2/code/naver", "/login/oauth2/code/kakao").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login() // OAuth2 로그인 설정 시작
                .loginPage("/login") // 로그인 페이지 경로 설정 (사용자 정의 로그인 페이지를 만들 경우)
                .defaultSuccessUrl("/user") // 로그인 성공 후 리디렉션될 경로 설정
                .userInfoEndpoint()
                .oidcUserService(oidcUserService()) // OIDC 사용자 서비스 설정 (카카오, 네이버 등의 OIDC 서비스에 따라 다름)
                .and()
                .and()
                .addFilterBefore(jwtAuthenticationFilter1(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return new OidcUserService();
    }


}
