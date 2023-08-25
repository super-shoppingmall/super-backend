package com.github.superbackend.repository.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String categoryName;
    private String productPrice;
    private String productQuantity;
    private String productImages;
    private String productDetail;
    private String productOnSale;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
