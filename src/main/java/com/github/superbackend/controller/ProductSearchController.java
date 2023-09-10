package com.github.superbackend.controller;

import com.github.superbackend.repository.product.Product;
import com.github.superbackend.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// 검색하는 기능
@RestController
@RequestMapping("/api/product/search")
public class ProductSearchController {
    @Autowired
    private ProductSearchService productSearchService;

    // 모든 제품 조회
    @GetMapping
    public List<Product> getAllProducts() {
        return productSearchService.getAllProducts();
    }

    // 특정 제품 ID로 조회
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productSearchService.getProductsById(id).orElse(null);
    }
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productSearchService.getProductsByName(name);
    }
}
