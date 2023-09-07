package com.github.superbackend.repository.paymoney;

import com.github.superbackend.repository.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymoneyRepository extends JpaRepository<Paymoney, Long> {

    Paymoney findAllByMember(Member member);
    Integer findPaymoneyByMember(Member member);
}
