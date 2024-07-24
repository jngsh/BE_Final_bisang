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
    @Select("SELECT * FROM cart_items WHERE cart_id = #{cartId}")
    List<CartItems> findByCartId(int cartId);

    @Select("SELECT * FROM cart_items WHERE cart_id = #{cartId} AND product_id = #{productId}")
    Optional<CartItems> findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

    @Insert("INSERT INTO cart_items(cart_id, product_id, amount) VALUES(#{cartId}, #{productId}, #{amount})")
    void insertCartItem(CartItems cartItem);

    @Update("UPDATE cart_items SET amount = #{amount} WHERE cart_item_id = #{cartItemId}")
    void updateCartItem(CartItems cartItem);
}
