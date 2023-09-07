package com.github.superbackend.controller;
import com.github.superbackend.config.JwtUtil;
import com.github.superbackend.dto.JwtResponse;
import com.github.superbackend.dto.LoginRequest;
import com.github.superbackend.dto.MemberDTO;
import com.github.superbackend.entity.Member;
import com.github.superbackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
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

        String jwt = jwtUtil.generateToken(String.valueOf(authentication));

        // 사용자 정보 추출 (예: 사용자 이름 및 권한)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // JwtResponse 객체 생성 및 반환
        return new JwtResponse(jwt, username, authorities);
    }
    // 회원가입 및 로그인 처리
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberDTO memberDTO) {
        // 클라이언트로부터 전달된 회원 정보(MemberDTO)를 추출
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
            String jwt = jwtUtil.generateToken(email);

            // 클라이언트에게 JWT 토큰을 반환
            return ResponseEntity.ok(jwt);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed");
        }
    }

}
