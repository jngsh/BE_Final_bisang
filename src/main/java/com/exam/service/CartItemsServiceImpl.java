package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.config.CartItemsMapper;
import com.exam.dto.CartItemsDTO;
import com.exam.entity.CartItems;
import com.exam.repository.CartItemsRepository;

@Service
//@RequiredArgsConstructor
public class CartItemsServiceImpl implements CartItemsService {

	@Autowired
	private CartItemsRepository cartItemsRepository; //이거 안되면 생성자로 바꿔야함

	CartItemsMapper cartItemsMapper;

	public CartItemsServiceImpl(CartItemsMapper cartItemsMapper) {
		this.cartItemsMapper = cartItemsMapper;
	}


	@Override
	public List<CartItemsDTO> findByCartId(int cartId) {
		ModelMapper mapper = new ModelMapper(); // 변환하기

		List<CartItems> list = cartItemsRepository.findByCartId(cartId);

		List<CartItemsDTO> cartItemsList = list.stream().map(e -> mapper.map(e, CartItemsDTO.class))
				.collect(Collectors.toList());

		return cartItemsList;
	}


	@Override
	public void removeAllItems(int cartId) {
		cartItemsMapper.removeAllItems(cartId);
	}

}