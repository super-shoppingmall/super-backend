package com.github.superbackend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="cart_Product")
public class CartProduct {

    @Id
    @GeneratedValue
    @Column(name = "cart_product_id")
    private Long cartProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public static CartProduct createCartProduct(Cart cart, Product product, int count) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setCount(count);
        return cartProduct;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){
        this.count = count;
    }

}