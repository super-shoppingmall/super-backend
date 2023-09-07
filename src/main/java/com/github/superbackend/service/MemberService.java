package com.github.superbackend.service;

import com.github.superbackend.dto.MemberDTO;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean isEmailAlreadyExists(String email) {
        Optional<Member> existingMember = Optional.ofNullable(memberRepository.findByEmail(email));
        return existingMember.isPresent();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다: " + email);
        }
        return new User(member.getEmail(), member.getPassword(), Collections.emptyList());
    }

    public UsernamePasswordAuthenticationToken authenticate(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return null;
    }

    public Member login(String email, String password) {
        // 여기에서 사용자 인증 로직을 구현
        UserDetails userDetails = loadUserByUsername(email);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            // 비밀번호가 일치하면 사용자 정보를 반환
            // 이 정보는 로그인된 사용자의 정보로 활용할 수 있습니다.
            // 사용자의 추가 정보가 필요하면 UserDetails 객체를 확장하여 추가 정보를 포함시킬 수 있습니다.

            // 여기서 User 클래스를 사용하여 UserDetails를 변환
            User user = (User) userDetails;

            // Member 클래스 생성자에 맞게 수정하여 사용자 정보를 반환
            return new Member();
        } else {
            // 로그인 실패시 예외 또는 null 값을 반환하거나 다른 방식으로 처리
            return null;
        }
    }

    public Member registerMember(MemberDTO memberDTO) {
        // 이메일을 기준으로 중복 회원 검사
       String email =  memberDTO.getEmail();
        String password = memberDTO.getPassword();

        if (memberRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 등록된 이메일 주소입니다.");
        }

        // 비밀번호 암호화

        String encodedPassword = passwordEncoder.encode(password);


        // 회원 정보 생성 및 저장
        Member newMember = new Member();
        newMember.setEmail(email);
        newMember.setPassword(encodedPassword);
        newMember.setPhone(memberDTO.getPhone());
        newMember.setAddress(memberDTO.getAddress()); // 주소 설정
        newMember.setGender(memberDTO.getGender());   // 성별 설정

        return memberRepository.save(newMember);
    }

    public boolean deleteMember(Long memberId) {
        // 회원 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 회원 상태를 'DELETED'로 변경
        member.setStatus("DELETED");

        // 변경된 상태를 데이터베이스에 업데이트
        memberRepository.save(member);

        return true; // 성공적으로 탈퇴 처리된 경우 true 반환
    }
}







