package com.github.superbackend.controller;

import com.github.superbackend.dto.CartDetailDto;
import com.github.superbackend.dto.CartOrderDto;
import com.github.superbackend.dto.CartProductDto;
import com.github.superbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Long> addCart(@RequestBody CartProductDto cartProductDto,
                                        @RequestHeader("email") String email) {
        Long cartProductId = cartService.addCart(cartProductDto, email);
        return ResponseEntity.ok(cartProductId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartDetailDto>> getCartList(@RequestHeader("email") String email) {
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(email);
        return ResponseEntity.ok(cartDetailDtoList);
    }

    @GetMapping("/validate/{cartProductId}")
    public ResponseEntity<Boolean> validateCartProduct(@PathVariable Long cartProductId,
                                                    @RequestHeader("email") String email) {
        boolean isValid = cartService.validateCartProduct(cartProductId, email);
        return ResponseEntity.ok(isValid);
    }

    @PutMapping("/updateCount/{cartProductId}")
    public ResponseEntity<Void> updateCartProductCount(@PathVariable Long cartProductId,
                                                    @RequestParam int count) {
        cartService.updateCartProductCount(cartProductId, count);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{cartProductId}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long cartProductId) {
        cartService.deleteCartProduct(cartProductId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order")
    public ResponseEntity<Long> orderCartProduct(@RequestBody List<CartOrderDto> cartOrderDtoList,
                                              @RequestHeader("email") String email) {
        Long orderId = cartService.orderCartProduct(cartOrderDtoList, email);
        return ResponseEntity.ok(orderId);
    }
}
