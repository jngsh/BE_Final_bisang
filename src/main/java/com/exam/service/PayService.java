package com.exam.service;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.SendToPayDTO;

public interface PayService {
    SendToPayDTO getSendToPayInfo(CartItemsDTO cartItemsDTO);
}
