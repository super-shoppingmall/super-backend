package com.github.superbackend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByItemIdAndRepimgYn(Long itemId, String imgYesOrNo);

}
