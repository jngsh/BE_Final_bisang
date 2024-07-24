package com.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.config.CartItemsMapper;
import com.exam.entity.CartItems;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartItemsMapper cartItemsMapper;

    public void addProductToCart(int cartId, int productId) {
        CartItems cartItem = cartItemsMapper.findByCartIdAndProductId(cartId, productId)
                .orElseGet(() -> {
                    CartItems newCartItem = new CartItems();
                    newCartItem.setCartId(cartId);
                    newCartItem.setProductId(productId);
                    newCartItem.setAmount(0);
                    cartItemsMapper.insertCartItem(newCartItem);
                    return newCartItem;
                });
        cartItem.setAmount(cartItem.getAmount() + 1);
        cartItemsMapper.updateCartItem(cartItem);
    }

    public List<CartItems> getCartItems(int cartId) {
        return cartItemsMapper.findByCartId(cartId);
    }
}
