package com.github.superbackend.config;

import com.github.superbackend.security.JwtAuthenticationFilter;
import com.github.superbackend.service.CustomOAuth2UserService;
import com.github.superbackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtUtil jwtUtil;
    private MemberService memberService;
    private UserDetailsService userDetailsService;

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
                .antMatchers("/user").hasAnyRole("USER") // /user 엔드포인트에 대한 접근 권한 설정
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/user")
                .userInfoEndpoint()
                .oidcUserService(oidcUserService())
                .and()
                .and()
                .addFilterBefore(jwtAuthenticationFilter1(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return new OidcUserService();
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

}
