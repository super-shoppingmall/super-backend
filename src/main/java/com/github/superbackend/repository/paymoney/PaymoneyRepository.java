package com.github.superbackend.repository.paymoney;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymoneyRepository extends JpaRepository<Paymoney, Long> {
}
