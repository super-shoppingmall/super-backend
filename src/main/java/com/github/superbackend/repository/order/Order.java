package com.github.superbackend.repository.order;


import com.github.superbackend.constant.OrderStatus;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.order.OrderProduct;
import com.github.superbackend.repository.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String address;
    private OrderStatus orderStatus;
    private Integer orderTotalPrice;
    private LocalDateTime orderedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProductList = new ArrayList<>();
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.setOrder(this);
    }
    public static Order createOrder(Member member, List<OrderProduct> orderProductList) {
        Order order = new Order();
        order.setMember(member);

        for(OrderProduct orderProduct : orderProductList) {
            order.addOrderProduct(orderProduct);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderedAt(LocalDateTime.now());
        return order;
    }
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.cancel();
        }
    }
}