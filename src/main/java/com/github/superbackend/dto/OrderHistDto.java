package com.github.superbackend.dto;


import com.github.superbackend.constant.OrderStatus;
import com.github.superbackend.repository.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {



    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태

    private List<OrderProductDto> orderProductDtoList = new ArrayList<>();

    //주문 상품리스트
    public void addOrderProductDto(OrderProductDto orderProductDto){
        orderProductDtoList.add(orderProductDto);
    }
    public OrderHistDto(Order order){
        this.orderId = order.getOrderId();
        this.orderDate = order.getOrderedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }
}