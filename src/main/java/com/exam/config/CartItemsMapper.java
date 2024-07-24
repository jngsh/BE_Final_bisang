package com.exam.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import com.exam.entity.CartItems;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartItemsMapper {
   
    List<CartItems> findByCartId(int cartId);

   
    Optional<CartItems> findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

    
    void insertCartItem(CartItems cartItem);

    
    void updateCartItem(CartItems cartItem);
}
