package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.CartItemsDTO;

@Mapper
public interface CartItemsMapper {

    CartItemsDTO findItemById(int cartItemId);

    List<CartItemsDTO> findItemsByCartId(int cartId);
    
    List<CartItemsDTO> findcartItemsProducts(int cartId);

    int addItem(CartItemsDTO item);

    void updateItemAmount(CartItemsDTO item);
    
    void updateShippingStatus(CartItemsDTO item);

    void removeItemFromCart(int cartItemId);

    void deleteItemsByCartId(int cartId);
}
