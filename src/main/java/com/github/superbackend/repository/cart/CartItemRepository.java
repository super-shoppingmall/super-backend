package com.github.superbackend.repository.cart;


import com.github.superbackend.dto.CartDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.github.superbackend.dto.CartDetailDto(ci.cartProductId, i.productName, i.productPrice, ci.count, im.productImageUrl) " +
            "from CartItem ci, ProductImage im " +
            "join ci.product i " +
            "where ci.cart.CartId = :cartId " +
            "and im.product.productId = ci.product.productId " +
            "and im.productImageUrl = 'Y' " +
            "order by ci.cartProductId desc"
            )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}