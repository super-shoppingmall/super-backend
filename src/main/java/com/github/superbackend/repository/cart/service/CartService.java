package com.github.superbackend.repository.cart.service;

import com.github.superbackend.repository.cart.dto.CartDetailDto;
import com.github.superbackend.repository.cart.dto.CartItemDto;
import com.github.superbackend.repository.cart.dto.CartOrderDto;
import com.github.superbackend.repository.cart.entity.Cart;
import com.github.superbackend.repository.cart.entity.CartItem;
import com.github.superbackend.repository.cart.repository.CartItemRepository;
import com.github.superbackend.repository.cart.repository.CartRepository;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.product.Product;
import com.github.superbackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    public Long addCart(CartItemDto cartItemDto, Long id){
        Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        // MemberRepository 보고 추가
        // Member member 생성
        // Cart cart 생성
        /* 수정 필요 부분*/
        Member member = memberRepository.findById(id);
        Cart cart = cartRepository.findByMemberId(id);
        //예외처리
        if (cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(),product.getProductId());

        if (savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getCartId();
        }
        else {
            CartItem cartItem = CartItem.createCartItem(product,cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getCartId();
        }


    }
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(Long id){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
        //수정
        Member member = memberRepository.findById(id);
        Cart cart = cartRepository.findByMemberId(member.getMemberId());
        //예외처리
        if (cart == null){
            return cartDetailDtoList;
        }
        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, Long Id){
        Member curMember = memberRepository.findById(Id);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        //수정
        Member savedMember = cartItem.getMemberId();
        if(!StringUtils.equals(curMember.getMemberId(), savedMember.getMemberId())){
            return false;
        }
        return true;
    }
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
    // order보고 작성
//    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, Long Id){
//    }
}
