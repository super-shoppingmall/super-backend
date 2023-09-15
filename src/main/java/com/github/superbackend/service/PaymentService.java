package com.github.superbackend.service;

import com.github.superbackend.dto.OrderHistDto;
import com.github.superbackend.dto.OrderProductDto;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.order.Order;
import com.github.superbackend.repository.order.OrderRepository;
import com.github.superbackend.repository.payment.Payment;
import com.github.superbackend.repository.payment.PaymentRepository;
import com.github.superbackend.repository.product.ProductImage;
import com.github.superbackend.repository.product.ProductImageRepository;
import com.github.superbackend.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public Payment savePayment(Long orderId, String email) {
        // 1. member 조회
        Member member = memberRepository.findByEmail(email);

        // 2. order 조회
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("주문 정보가 없습니다.")
        );

        // 3. payment save
        Payment payment = paymentRepository.save(Payment.builder()
                        .member(member)
                        .order(order)
                        .paidAt(LocalDateTime.now())
                        .build());

        return payment;
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> findPayment(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email);

        // 1. 멤버별 페이먼트 찾기
        List<Payment> payments = paymentRepository.findAllByMember(member);

        if (payments.size() == 0) return null;

        List<OrderHistDto> orderHistDtoList = new ArrayList<>();

        // 3. 주문에서 OrderProducts...
        for(Payment payment : payments) {
            OrderHistDto orderHistDto = new OrderHistDto(payment.getOrder());

            payment.getOrder().getOrderProductList().forEach(
                    orderProduct -> {
                        ProductImage productImage = productImageRepository.findByProduct(orderProduct.getProduct().getProductId());
                        OrderProductDto orderProductDto =
                                new OrderProductDto(orderProduct, productImage.getProductImageUrl());
                        orderHistDto.addOrderProductDto(orderProductDto);
                    }
            );

            orderHistDtoList.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtoList, pageable, orderHistDtoList.size());

    }
}
