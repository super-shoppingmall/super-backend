package com.github.superbackend.repository.cart.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    @NotNull
    private Long productId;

    private int count;
}
