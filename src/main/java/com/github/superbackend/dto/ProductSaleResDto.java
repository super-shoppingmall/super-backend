package com.github.superbackend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductSaleResDto {
    private Long productId;
    private Long memberId; // 판매자의 memberId
    private String productName;
    private String categoryName;
    private int productPrice;
    private int productQuantity;
    private List<String> productImageUrls; // s3에서 이미지 경로
    private String productDetail;
    private LocalDate closingAt; // 판매 종료일
    private String productStatus; // 상품 상태 추가


}
