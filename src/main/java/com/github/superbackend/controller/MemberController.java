package com.github.superbackend.controller;

import com.github.superbackend.config.JwtUtil;
import com.github.superbackend.dto.MemberDTO;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public MemberController(MemberService memberService, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
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
    // hyuna
    @ApiOperation("회원 유저 정보 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long memberId) {
        MemberDTO member = memberService.getMember(memberId);

        // TODO : aboutMe..
        if (member != null) {
            return ResponseEntity.ok(member);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation("회원 유저 정보 수정")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDTO> updateMember(MemberDTO memberDTO, @PathVariable Long memberId) {
        MemberDTO member = memberService.updateMember(memberDTO, memberId);

        if (member != null) {
            return ResponseEntity.ok(member);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
