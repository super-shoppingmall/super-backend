package com.github.superbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class CartProductDto {
    @NotNull
    private Long productId;

    private int count;
}
