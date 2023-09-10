package com.github.superbackend.controller;

import com.github.superbackend.dto.ProductSaleReqDto;
import com.github.superbackend.dto.ProductSaleResDto;
import com.github.superbackend.service.ProductSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products/sale")
public class ProductSaleController {
    private final ProductSaleService productSaleService;

    // 쇼핑몰 판매 물품 등록
    @PostMapping("/register")
    public ResponseEntity<ProductSaleResDto> registerProductSale(
            ProductSaleReqDto productSaleReqDto,
            @AuthenticationPrincipal UserDetails userDetails) { // 유저 추가
        /*System.out.println("유저 뭐로 가져옴?"+userDetails.getUsername());*/
        String email = "test@naver.com";


        ProductSaleResDto result = productSaleService.registerProductSale(userDetails.getUsername()/* email*/, productSaleReqDto);


        return ResponseEntity.ok(result);

    }
}
