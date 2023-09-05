package com.github.superbackend.repository.cart.repository;


import com.github.superbackend.repository.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}