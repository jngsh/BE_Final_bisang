package com.exam.service;

import com.exam.entity.Carts;
import com.exam.entity.CartItems;
import com.exam.entity.Products;
import com.exam.repository.CartsRepository;
import com.exam.config.CartsMapper;
import com.exam.config.CartItemsMapper;
import com.exam.config.ProductsMapper;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import com.exam.dto.ProductsDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    CartsMapper cartsMapper;
    CartItemsMapper cartItemsMapper;
    ProductsMapper productsMapper;
    CartsRepository cartsRepository;

    @Autowired
    public CartServiceImpl(CartsMapper cartsMapper, CartItemsMapper cartItemsMapper, ProductsMapper productsMapper, CartsRepository cartsRepository) {
        this.cartsMapper = cartsMapper;
        this.cartItemsMapper = cartItemsMapper;
        this.productsMapper = productsMapper;
        this.cartsRepository = cartsRepository;
    }

    @Override
    public CartsDTO getCartById(int cartId) {
        return cartsMapper.findCartById(cartId);
    }

    @Override
    public List<CartItemsDTO> getItemsByCartId(int cartId) {
        return cartItemsMapper.findItemsByCartId(cartId);
    }

    @Override
    @Transactional
    public int addItem(CartItemsDTO item) {
        // 제품 존재 여부 확인
        ProductsDTO product = productsMapper.findProductById(item.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("유효하지 않은 제품 ID입니다.");
        }

        // 카트 존재 여부 확인
        CartsDTO cart = cartsMapper.findCartById(item.getCartId());
        if (cart == null) {
            throw new IllegalArgumentException("유효하지 않은 카트 ID입니다.");
        }

        // 카트에 아이템 추가
        return cartItemsMapper.addItem(item);
    }

    @Override
    @Transactional
    public void updateItemAmount(int cartId, int productId, int amount) {
        // cartItemId가 메서드에 전달된 시점에서 값을 로깅
        logger.info("Received cartId: {}, productId: {}, amount: {}", cartId, productId, amount);

        if (cartId == 0) {
            logger.error("cartId is 0, which is not valid");
            throw new IllegalArgumentException("cartId cannot be 0");
        }
        
        Map<String, Object> map = new HashMap<>();
        map.put("cartId", cartId);
        map.put("productId", productId);
        
        CartItemsDTO item = cartItemsMapper.findProductById(map);
        if (item == null) {
            throw new IllegalArgumentException("해당 카트 아이템을 찾을 수 없습니다.");
        }
        
        item.setAmount(amount);
        cartItemsMapper.updateItemAmount(item);
    }

    
    @Override
    @Transactional
    public void updateShippingStatus(int cartItemId, boolean isShipping) {
        CartItemsDTO item = cartItemsMapper.findItemById(cartItemId);
        if (item == null) {
            throw new IllegalArgumentException("해당 카트 아이템을 찾을 수 없습니다.");
        }
        item.setShipping(isShipping);
        cartItemsMapper.updateShippingStatus(item);
    }

    @Override
    @Transactional
    public void removeItemFromCart(int cartItemId) {
        cartItemsMapper.removeItemFromCart(cartItemId);
    }

    @Override
    @Transactional
    public void clearCart(int cartId) {
        cartItemsMapper.deleteItemsByCartId(cartId);
    }

	@Override
	public List<CartItemsDTO> findcartItemsProducts(int cartId) {
		
		return cartItemsMapper.findcartItemsProducts(cartId);
	}
	
	@Override
	public Integer getCartIdByUserId(Integer userId) {
		Carts cart = cartsRepository.findByUsersUserId(userId);
		return cart != null? cart.getCartId() : null;
	}
}

