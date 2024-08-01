package com.exam.service;

import java.util.List;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.dto.SendToPayDTO;

public interface PayService {
    SendToPayDTO sendToPayInfo(CartItemsDTO cartItemsDTO);
//    List<ProductsDTO> findByCartId(CartItemsDTO cartId);
}
