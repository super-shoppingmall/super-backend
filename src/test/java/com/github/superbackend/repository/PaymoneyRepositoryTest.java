package com.github.superbackend.repository;

import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.paymoney.Paymoney;
import com.github.superbackend.repository.paymoney.PaymoneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class PaymoneyRepositoryTest {
    @Autowired
    private PaymoneyRepository paymoneyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("PaymoneyRepository의 페이머니 등록, 차감 테스트, 검색")
    @Test
    void saveAndFind() {
        // 멤버등록
        Member member = Member.builder()
                .memberId(1L)
                .email("test@test.com")
                .password("1234")
                .build();

        memberRepository.save(member);

        // 페이머니 충전
        Paymoney paymoney = paymoneyRepository.save(Paymoney.builder()
                .paymoneyId(1L)
                .member(member)
                .paymoney(30000)
                .build()
        );

        log.info("페이머니 save : " + paymoney.getPaymoney().toString());

        // 페이머니 조회
        paymoney = paymoneyRepository.findAllByMember(member);

        log.info("페이머니 select : " + paymoney.getPaymoney().toString());
    }
}