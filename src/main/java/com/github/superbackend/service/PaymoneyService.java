package com.github.superbackend.service;

import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.paymoney.Paymoney;
import com.github.superbackend.repository.paymoney.PaymoneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymoneyService {
    private final PaymoneyRepository paymoneyRepository;

    @Transactional
    public Paymoney savePaymoney(Long memberId, Integer money, boolean isCharge) {
        // 페이머니를 조회한 후
        Paymoney paymoney = findPaymoney(memberId);

        // 페이머니 충전의 경우
        if (isCharge) {
            // 페이머니가 존재하면 들어온 값을 그 위에 더하기(Update)
            if (paymoney != null) {
                paymoney.setPaymoney(paymoney.getPaymoney() + money);

                return paymoneyRepository.save(paymoney);
            }
            // 페이머니가 존재하지 않으면 새로 저장(Insert)
            else {
                return paymoneyRepository.save(
                        Paymoney.builder()
                                .member(Member.builder().memberId(memberId).build())
                                .paymoney(money)
                                .build()
                );
            }
        }
        // 페이머니 차감(결제)의 경우
        else {
            // 페이머니가 존재하면 들어온 값을 기존 값에서 차감하기(Update)
            if (paymoney != null) {
                paymoney.setPaymoney(paymoney.getPaymoney() - money);

                return paymoneyRepository.save(paymoney);
            }
            // 페이머니가 존재하지 않으면 예외처리
            else {
                return null;
            }
        }
    }

    @Transactional(readOnly = true)
    public Paymoney findPaymoney(Long memberId) {
        Paymoney paymoney = paymoneyRepository.findAllByMember(
                Member.builder()
                        .memberId(memberId)
                        .build()
        );

        return paymoney;
    }




} // end class
