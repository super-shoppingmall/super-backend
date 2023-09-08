package com.github.superbackend.service;

import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.paymoney.Paymoney;
import com.github.superbackend.repository.paymoney.PaymoneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class PaymoneyServiceTest {
    @Mock
    private PaymoneyRepository paymoneyRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PaymoneyService paymoneyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("paymoney 충전, 결제 테스트")
    @Test
    void savePaymoneyInsert() {
        // GIVEN
        Integer money = 1000;
        // member
        Member member = Member.builder()
                .memberId(1L)
                .email(null)
                .password(null)
                .build();

        memberRepository.save(member);

        Paymoney paymoney =  Paymoney.builder()
                .paymoneyId(1L)
                .member(member)
                .paymoney(money)
                .build();

        // when
        when(paymoneyRepository.save(paymoney)).thenReturn(paymoney);

        // 결과
        assertEquals(1L, paymoney.getPaymoneyId());
        log.info("페이머니 충전 Insert : " + paymoney.getPaymoney().toString());
    }

    @DisplayName("paymoney 조회 테스트")
    @Test
    void selectPaymoney() {
        // GIVEN
        Integer money = 1000;
        // member
        Member member = Member.builder()
                .memberId(1L)
                .email(null)
                .password(null)
                .build();

        memberRepository.save(member);

        Paymoney paymoney =  Paymoney.builder()
                .paymoneyId(1L)
                .member(member)
                .paymoney(money)
                .build();

        when(paymoneyRepository.save(paymoney)).thenReturn(paymoney);

        when(paymoneyService.findPaymoney(member.getMemberId())).thenReturn(paymoney);

        // verify
        assertEquals(1000, paymoney.getPaymoney());
        log.info("페이머니 조회 find : " + paymoney.getPaymoney().toString());
    }
} // end class