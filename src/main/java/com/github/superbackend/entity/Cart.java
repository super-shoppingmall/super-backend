//package com.github.superbackend.entity;
//
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "cart")
//@Getter @Setter
//@ToString
//public class Cart {
//
//    @Id
//    @Column(name = "cart_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long cartId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    public static Cart createCart(Member member){
//        Cart cart = new Cart();
//        cart.setMember(member);
//        return cart;
//    }
//
//}