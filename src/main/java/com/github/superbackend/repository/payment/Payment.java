//package com.github.superbackend.repository.payment;
//
//import com.github.superbackend.repository.order.Order;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long paymentId;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    private LocalDateTime paidAt;
//
//}
