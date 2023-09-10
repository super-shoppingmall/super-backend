package com.github.superbackend.service;

import com.github.superbackend.repository.product.Product;
import com.github.superbackend.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 재명 추가 검색 조회하는 기능
@Service
public class ProductSearchService {
    @Autowired
    private ProductRepository productRepository;
    public Optional<Product> getProductsById(Long id) {
        return productRepository.findById(id);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}
