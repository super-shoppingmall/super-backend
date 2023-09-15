package com.github.superbackend.controller;

import com.github.superbackend.config.JwtUtil;
import com.github.superbackend.dto.JwtResponse;
import com.github.superbackend.dto.LoginRequest;
import com.github.superbackend.dto.MemberDTO;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberService memberService;
    private final AuthenticationProvider myAuthenticationProvider; // 주입
    private final AuthenticationManager authenticationManager; // 주입


    @Autowired
    public AuthController(MemberService memberService, AuthenticationProvider myAuthenticationProvider, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.myAuthenticationProvider = myAuthenticationProvider; // 주입
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        // 인증 후 JWT 토큰 생성
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 사용자 정보 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // 이메일 가져오기

        // memberId를 어떻게 얻는지에 따라서 다음과 같이 memberId를 얻을 수 있을 것입니다.
        // 예: memberId를 사용자 정보에서 조회하는 메서드를 호출하여 얻는다고 가정
        Long memberId = memberService.getMemberIdByEmail(email); // memberId 조회 예시

        // JwtResponse 객체 생성 및 반환
        String jwt = jwtUtil.generateToken(memberId, email);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return new JwtResponse(jwt, email, authorities);
    }
    // 회원가입 및 로그인 처리
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberDTO memberDTO) {
        // 클라이언트로부터 전달된 회원 정보(MemberDTO)를 추출
        String memberId = memberDTO.getMemberId();
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();
        String phone = memberDTO.getPhone();

        // 이메일 중복 확인 로직을 추가하고, 중복된 경우 에러 응답을 반환할 수 있음
        if (memberService.isEmailAlreadyExists(email)) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // 회원 가입 메서드 호출
        Member registeredMember = memberService.registerMember(memberDTO);

        if (registeredMember != null) {
            // 회원가입 성공 시 JWT 토큰 생성
            String jwt = jwtUtil.generateToken(Long.parseLong(memberId), String.valueOf(Integer.parseInt(email)));

            // 클라이언트에게 JWT 토큰을 반환
            return ResponseEntity.ok(jwt);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed");
        }
    }

}
