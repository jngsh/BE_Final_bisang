package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.CartItemsDTO;

@Mapper
public interface CartItemsMapper {

    CartItemsDTO findItemById(int cartItemId);
    
    CartItemsDTO findProductById(Map<String, Object> map);

    List<CartItemsDTO> findItemsByCartId(int cartId);
    
    List<CartItemsDTO> findcartItemsProducts(int cartId);

    int addItem(CartItemsDTO item);

    void updateItemAmount(CartItemsDTO item);
    
    void updateShippingStatus(CartItemsDTO item);

    void removeItemFromCart(int cartItemId);

    void deleteItemsByCartId(int cartId);
    
    void removeAllItems(int cartId);

}
