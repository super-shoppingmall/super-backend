package com.github.superbackend.repository.member;

import com.github.superbackend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    boolean existsByEmail(String email);
    Member findByEmailAndPassword(String email, String password);

    // 회원 ID를 기반으로 회원 상태를 업데이트하는 메서드
    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.status = :status WHERE m.id = :id")
    void updateMemberStatus(Long id, String status);
}
