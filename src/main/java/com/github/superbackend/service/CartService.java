package com.github.superbackend.service;

import com.github.superbackend.dto.CartDetailDto;
import com.github.superbackend.dto.CartOrderDto;
import com.github.superbackend.dto.CartProductDto;
import com.github.superbackend.dto.OrderDto;
import com.github.superbackend.repository.CartProductRepository;
import com.github.superbackend.repository.cart.Cart;
import com.github.superbackend.repository.cart.CartProduct;
import com.github.superbackend.repository.cart.CartRepository;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.product.Product;
import com.github.superbackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderService orderService;

    public Long addCart(CartProductDto cartProductDto, String email){

        Product product = productRepository.findById(cartProductDto.getProductId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMember_MemberId(member.getMemberId());
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartProduct savedCartProduct = cartProductRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), product.getProductId());

        if(savedCartProduct != null){
            savedCartProduct.addCount(cartProductDto.getCount());
            return savedCartProduct.getCartProductId();
        } else {
            CartProduct cartProduct = CartProduct.createCartProduct(cart, product, cartProductDto.getCount());
            cartProductRepository.save(cartProduct);
            return cartProduct.getCartProductId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember_MemberId(member.getMemberId());
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartProductRepository.findCartDetailDtoList(cart.getCartId());
        return cartDetailDtoList;
    }

    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String email){
        Member curMember = memberRepository.findByEmail(email);
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartProduct.getCart().getMember();

        if(!StringUtils.pathEquals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void updateCartProductCount(Long cartProductId, int count){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);

        cartProduct.updateCount(count);
    }

    public void deleteCartProduct(Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        cartProductRepository.delete(cartProduct);
    }

    public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartProduct cartProduct = cartProductRepository
                            .findById(cartOrderDto.getCartProductId())
                            .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(cartProduct.getProduct().getProductId());
            orderDto.setCount(cartProduct.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartProduct cartProduct = cartProductRepository
                            .findById(cartOrderDto.getCartProductId())
                            .orElseThrow(EntityNotFoundException::new);
            cartProductRepository.delete(cartProduct);
        }

        return orderId;
    }

}