package com.github.superbackend.service;

import com.github.superbackend.entity.Member;
import com.github.superbackend.repository.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberService memberService; // 사용자 정보를 가져오는 서비스
    private final MemberRepository memberRepository;
    public UserDetailsServiceImpl(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 사용자 정보를 가져오는 메서드를 호출하여 UserDetails를 생성합니다.
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }
        // UserDetails를 생성하여 반환합니다.
        return member.toUserDetails();
    }
}