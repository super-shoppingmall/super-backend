package com.github.superbackend.dto;


import com.github.superbackend.repository.order.OrderProduct;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProductDto {
    private String productName; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로
    public OrderProductDto(OrderProduct orderProduct, String imgUrl){
        this.productName = orderProduct.getProduct().getProductName();
        this.count = orderProduct.getCount();
        this.orderPrice = orderProduct.getOrderPrice();
        this.imgUrl = imgUrl;
    }



}