package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.CartItemsDTO;
import com.exam.entity.CartItems;
import com.exam.repository.CartItemsRepository;

@Service
//@RequiredArgsConstructor
public class CartItemsServiceImpl implements CartItemsService {

	private CartItemsRepository cartItemsRepository;

	public CartItemsServiceImpl(CartItemsRepository cartItemsRepository) {
		this.cartItemsRepository = cartItemsRepository;
	}

	@Override
	public List<CartItemsDTO> findByCartId(int cartId) {
		ModelMapper mapper = new ModelMapper(); // 변환하기

		List<CartItems> list = cartItemsRepository.findByCartId(cartId);

		List<CartItemsDTO> cartItemsList = list.stream().map(e -> mapper.map(e, CartItemsDTO.class))
				.collect(Collectors.toList());

		return cartItemsList;
	}

}