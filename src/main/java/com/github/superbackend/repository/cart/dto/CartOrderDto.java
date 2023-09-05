package com.github.superbackend.repository.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {
    private Long cartItemId;

    private List<CartOrderDto> cartOrderList;
}
