package com.github.superbackend.repository.cart.entity;

import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.product.Product;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product productId;

    private Integer cartCount;
    private Integer cartTotalPrice;

    public static CartItem createCartItem(Product product, int count){
        CartItem cartItem = new CartItem();
        cartItem.setProductId(product);
        cartItem.setCartCount(count);
        return cartItem;
    }
    public void addCount(int count){
        this.cartCount += count;
    }
    public void updateCount(int count){
        this.cartCount = count;
    }

}
