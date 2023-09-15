package com.github.superbackend.service;


import com.github.superbackend.dto.OrderDto;
import com.github.superbackend.dto.OrderHistDto;
import com.github.superbackend.dto.OrderProductDto;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.order.Order;
import com.github.superbackend.repository.order.OrderProduct;
import com.github.superbackend.repository.order.OrderRepository;
import com.github.superbackend.repository.product.Product;
import com.github.superbackend.repository.product.ProductImage;
import com.github.superbackend.repository.product.ProductImageRepository;
import com.github.superbackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final ProductImageRepository productImageRepository;

    public Long order(OrderDto orderDto, String email){

        Product product = productRepository.findById(orderDto.getOrderId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
        orderProductList.add(orderProduct);
        Order order = Order.createOrder(member, orderProductList);
        orderRepository.save(order);

        return order.getOrderId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderProduct> orderProductList = order.getOrderProductList();
            for (OrderProduct orderProduct : orderProductList) {
                ProductImage productImage = productImageRepository.findByProductProductIdAndRepImgYn(orderProduct.getProduct().getProductId(),"Y");
                OrderProductDto orderProductDto =
                        new OrderProductDto(orderProduct, productImage.getProductImageUrl());
                orderHistDto.addOrderProductDto(orderProductDto);
            }

            orderHistDtoList.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtoList, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.pathEquals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderProduct> orderProductList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Product product = productRepository.findById(orderDto.getOrderId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(member, orderProductList);
        orderRepository.save(order);

        return order.getOrderId();
    }

}