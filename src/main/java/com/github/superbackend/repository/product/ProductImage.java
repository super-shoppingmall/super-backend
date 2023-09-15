package com.github.superbackend.repository.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductImage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    // 이재명 작성
    // 대표적인 상품이미지
    // 아이템 조회 할때 사용
    private String repImgYn;

    private String productImageUrl; // 이미지 경로를 문자열로 저장
    public ProductImage(String imageUrl, Product product) {
        this();
        this.product = product;
        this.productImageUrl = imageUrl;
    }

}
