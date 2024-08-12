package com.exam.service;

import java.util.List;

import com.exam.dto.CartItemsDTO;

public interface CartItemsService {
    List<CartItemsDTO> findByCartId(int cartId);
    public void removeAllItems(int cartId);
}
