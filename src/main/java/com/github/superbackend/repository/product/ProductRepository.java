package com.github.superbackend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 재명 추가
    // 이름 검색
    List<Product> findByNameContaining(String productName);
}
