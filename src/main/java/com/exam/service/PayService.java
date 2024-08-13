package com.exam.service;

import java.util.List;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.SendToPayDTO;

public interface PayService {
    SendToPayDTO sendToPayInfo(CartItemsDTO cartItemsDTO);
//    List<ProductsDTO> findProductByCartId(int cartId);
    
//    public List<OrderedDetailDTO> getCartItems(int cartid);
    public List<CartItemsDTO> getCartItems(int cartId);

    
}
