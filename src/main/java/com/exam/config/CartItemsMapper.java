package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.CartItemsDTO;

@Mapper
public interface CartItemsMapper {

    CartItemsDTO findItemById(int cartItemId);

    List<CartItemsDTO> findItemsByCartId(int cartId);

    int addItem(CartItemsDTO item);

    void updateItem(CartItemsDTO item);

    void deleteItem(int cartItemId);

    void deleteItemsByCartId(int cartId);
}
