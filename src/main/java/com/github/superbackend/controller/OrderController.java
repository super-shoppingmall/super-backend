package com.github.superbackend.controller;

import com.github.superbackend.dto.OrderDto;
import com.github.superbackend.dto.OrderHistDto;
import com.github.superbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


// Post
// 주문 생성
// Get
// 주문 목록을 페이지로 가져옴
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> placeOrder(@RequestBody OrderDto orderDto, Principal principal) {
        Long orderId = orderService.order(orderDto, principal.getName());
        return ResponseEntity.ok(orderId);
    }

    @GetMapping
    public ResponseEntity<Page<OrderHistDto>> getOrderList(Principal principal, Pageable pageable) {
        Page<OrderHistDto> orders = orderService.getOrderList(principal.getName(), pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Boolean> validateOrder(@PathVariable Long orderId, Principal principal) {
        boolean isValid = orderService.validateOrder(orderId, principal.getName());
        return ResponseEntity.ok(isValid);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order has been canceled.");
    }

    @PostMapping("/bulk")
    public ResponseEntity<Long> placeMultipleOrders(@RequestBody List<OrderDto> orderDtoList, Principal principal) {
        Long lastOrderId = orderService.orders(orderDtoList, principal.getName());
        return ResponseEntity.ok(lastOrderId);
    }
}