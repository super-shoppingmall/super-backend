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

    private String productImageUrl; // 이미지 경로를 문자열로 저장
    public ProductImage(String imageUrl, Product product) {
        this();
        this.product = product;
        this.productImageUrl = imageUrl;
    }

}
