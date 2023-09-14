//package com.github.superbackend.repository;
//
//import com.github.superbackend.entity.ProductImage;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
//    // 이재명 작성
//    // 이미지 제품 id와 이미지 유무로 찾는 기능
//    ProductImage findByProductProductIdAndRepImgYn(Long productId, String repimgYn);
//}