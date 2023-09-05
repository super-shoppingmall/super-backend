package com.github.superbackend.repository.cart.repository;

import com.github.superbackend.repository.cart.dto.CartDetailDto;

import com.github.superbackend.repository.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
    //쿼리 추가

    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}