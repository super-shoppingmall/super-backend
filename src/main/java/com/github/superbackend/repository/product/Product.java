package com.github.superbackend.repository.product;


import com.github.superbackend.repository.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String categoryName;
    private int productPrice;
    private int productQuantity;

<<<<<<< HEAD
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // 저장, 수정, 삭제 같이됨.
    //@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
=======
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 저장, 수정, 삭제 같이됨.
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
    private List<ProductImage> images = new ArrayList<>();  //productImages;

    private String productDetail;
    //private String productOnSale;
    private LocalDate closingAt; // 판매 종료

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 등록한 멤버 (판매자 또는 구매자)

    // @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
    // private List<Order> orders;  // 해당 상품에 대한 모든 주문들

    @Column(name = "product_status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

