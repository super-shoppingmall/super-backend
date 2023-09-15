//package com.github.superbackend.repository;
//
//
//import com.github.superbackend.constant.ProductStatus;
//import com.github.superbackend.entity.Member;
//import com.github.superbackend.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface ProductRepository extends JpaRepository<Product, Long> {
//
//    List<Product> findByMemberAndClosingAtAfter(Member member, LocalDate now);
//    @Modifying
//    @Query("update Product p set p.productStatus = :status where p.closingAt < :now")
//    int updateStatusForPastProducts(@Param("status") ProductStatus status, @Param("now") LocalDate now);
//}