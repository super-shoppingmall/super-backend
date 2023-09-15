//package com.github.superbackend.entity;
//
//
//import com.github.superbackend.constant.ProductStatus;
//import com.github.superbackend.exception.OutOfStockException;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Builder
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Product{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "product_id")
//    private Long productId;
//    private String productName;
//    private String categoryName;
//    private int productPrice;
//    private int productQuantity;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 저장, 수정, 삭제 같이됨.
//    private List<ProductImage> images = new ArrayList<>();  //productImages;
//
//    private String productDetail;
//    //private String productOnSale;
//    private LocalDate closingAt; // 판매 종료
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;  // 등록한 멤버 (판매자 또는 구매자)
//
//    // @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
//    // private List<Order> orders;  // 해당 상품에 대한 모든 주문들
//
//    @Column(name = "product_status")
//    @Enumerated(EnumType.STRING)
//    private ProductStatus productStatus;
//
//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
//
//    // 재명 추가
//    // 재고 삭제
//    public void removeStock(int productQuantity){
//        int restStock = this.productQuantity - productQuantity;
//        if(restStock<0){
//            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.productQuantity + ")");
//        }
//        this.productQuantity = restStock;
//    }
//    // 재고 추가
//    public void addStock(int productQuantity){
//        this.productQuantity += productQuantity;
//    }
//}