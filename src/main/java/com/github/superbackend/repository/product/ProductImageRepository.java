package com.github.superbackend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductProductId(Long productId);
    // 이재명 작성
    // 이미지 제품 id와 이미지 유무로 찾는 기능
    ProductImage findByProductProductIdAndRepImgYn(Long productId, String repimgYn);

    // hyuna
    ProductImage findByProduct(Long productId);
}
