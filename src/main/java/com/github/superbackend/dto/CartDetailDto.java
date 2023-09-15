package com.github.superbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDto {

    private Long cartProductId; //장바구니 상품 아이디

    private String ProductName; //상품명

    private int price; //상품 금액

    private int count; //수량

    private String imgUrl; //상품 이미지 경로

    public CartDetailDto(Long cartProductId, String ProductName, int price, int count, String imgUrl){
        this.cartProductId = cartProductId;
        this.ProductName = ProductName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }

}