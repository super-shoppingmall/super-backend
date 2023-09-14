
package com.github.superbackend.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMember(Long memberId);
    Cart findByMember_MemberId(Long memberId);

}
