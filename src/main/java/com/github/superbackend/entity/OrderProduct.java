package com.github.superbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "order_product")
public class OrderProduct {

    @Id @GeneratedValue
    @Column(name = "order_product_id")
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public static OrderProduct createOrderProduct(Product product, int count){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setCount(count);
        orderProduct.setOrderPrice(product.getProductPrice());
        product.removeStock(count);
        return orderProduct;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancel() {
        this.getProduct().addStock(count);
    }

}