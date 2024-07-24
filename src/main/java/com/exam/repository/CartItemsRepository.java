package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.CartItems;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    
    // 특정 cart_id에 대한 장바구니 항목 조회
    List<CartItems> findByCartId(int cartId);
}
