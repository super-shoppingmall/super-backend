package com.github.superbackend.repository;

import com.github.superbackend.dto.CartDetailDto;
import com.github.superbackend.repository.cart.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    CartProduct findByCart_CartIdAndProduct_ProductId(Long cartId, Long ProductId);

    @Query("select new com.github.superbackend.dto.CartDetailDto(cp.cartProductId, p.productName, p.productPrice, cp.count, pi.productImageUrl) " +
            "from CartProduct cp, ProductImage pi " +
            "join cp.product p " +
            "where cp.cart.cartId = :cartId " +
            "and pi.product.productId = cp.product.productId " +
            "and pi.repImgYn= 'Y' " +
            "order by cp.createdAt desc"
            )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}