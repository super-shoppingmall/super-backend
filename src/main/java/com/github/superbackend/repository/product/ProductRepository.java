package com.github.superbackend.repository.product;

import com.github.superbackend.repository.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("update Product p set p.productStatus = :status where p.closingAt < :now")
    int updateStatusForPastProducts(@Param("status") ProductStatus status, @Param("now") LocalDate now);

    List<Product> findByMemberAndProductStatus(Member seller, ProductStatus status);

    @Query("SELECT e FROM Product e WHERE e.productStatus = 'FINISHED' OR e.productStatus = 'CLOSED'")
    List<Product> findByMemberAndProductStatusIsNotActive();

    Product findByProductIdAndMember(Long productId, Member seller);
    List<Product> findByMemberAndClosingAtAfter(Member member, LocalDate now);
    @Modifying
    @Query("update Product p set p.productStatus = :status where p.closingAt < :now")
    int updateStatusForPastProducts(@Param("status") com.github.superbackend.constant.ProductStatus status, @Param("now") LocalDate now);
}
