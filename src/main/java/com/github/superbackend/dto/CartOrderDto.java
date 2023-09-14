package com.github.superbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartProductId;

    private List<CartOrderDto> cartOrderDtoList;

}