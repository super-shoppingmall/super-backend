package com.github.superbackend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductProductId(Long productId);
=======
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
}
