package com.github.superbackend.controller;

import com.github.superbackend.dto.ProductSaleReqDto;
import com.github.superbackend.dto.ProductSaleResDto;
import com.github.superbackend.service.ProductSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    // 쇼핑몰 판매 물품 조회 (판매 중인 물품만)
    @GetMapping("/myproducts")
    public ResponseEntity<List<ProductSaleResDto>> getMyProductsOnSale(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ProductSaleResDto> products = productSaleService.getProductsOnSale(userDetails.getUsername());
        return ResponseEntity.ok(products);
    }

    // 상품 상태 업데이트 (판매 종료 날짜가 지난 상품의 상태를 CLOSED로 변경)
    @PatchMapping("/updateStatus")
    public ResponseEntity<Void> updateProductStatus() {
        productSaleService.updateProductStatus();
        return ResponseEntity.noContent().build();
    }


}
