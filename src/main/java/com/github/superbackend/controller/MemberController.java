package com.github.superbackend.controller;

import com.github.superbackend.config.JwtUtil;
import com.github.superbackend.dto.MemberDTO;
import com.github.superbackend.entity.Member;
import com.github.superbackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDTO memberDTO) {

        Member registeredMember = memberService.registerMember(memberDTO);
        if (registeredMember != null) {
            return ResponseEntity.ok("회원가입 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Member member = memberService.login(email, password);
        if (member != null) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();
        String phone = memberDTO.getPhone();
        String address = memberDTO.getAddress(); // 주소 정보 추가
        String gender = memberDTO.getGender();   // 성별 정보 추가

        if (memberService.isEmailAlreadyExists(email)) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        Member registeredMember = memberService.registerMember(memberDTO);

        if (registeredMember != null) {
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Signup failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestBody String email) {
        if (memberService.isEmailAlreadyExists(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } else {
            return ResponseEntity.ok("Email is available");
        }
    }
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        // 회원 탈퇴 메서드 호출
        boolean result = memberService.deleteMember(memberId);

        if (result) {
            return ResponseEntity.ok("회원 탈퇴 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 실패");
        }
    }

}