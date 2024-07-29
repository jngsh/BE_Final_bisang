package com.exam.service;

import com.exam.dto.CartItemsDTO;

import java.util.List;

public interface CartItemsService {
    List<CartItemsDTO> findByCartId(int cartId);
}
