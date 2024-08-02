package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.dto.SendToPayDTO;

import lombok.extern.slf4j.Slf4j;
////////////////////////////////////////////////////////
//CartItems에 결제시 필수요소인 상품명, 가격이 존재하지 않음
//여러 건 결제 시 'ㅇㅇ외 ㅇ건', '합산금액'을 생성해서 넘기기 위한 작업을 위한 service
////////////////////////////////////////////////////////

@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

	@Autowired
	private CartItemsService cartItemsService;

	@Autowired
	private ProductsService productsService;

	@Override
	public SendToPayDTO sendToPayInfo(CartItemsDTO cartItemsDTO) {

		List<CartItemsDTO> cartItems = cartItemsService.findByCartId(cartItemsDTO.getCartId());

		List<ProductsDTO> products = cartItems.stream()
				.map(item -> productsService.findByProductId(item.getProductId())).collect(Collectors.toList());
		log.info("CartItemsDTO:{}", cartItemsDTO);
		log.info("cartItems:{}", cartItems);
		log.info("products:{}", products);

		String combinedName;
		if (products.size() > 1) {
			combinedName = products.get(0).getProductName() + " 외 " + (products.size() - 1) + "건";
		} else {
			combinedName = products.stream().map(ProductsDTO::getProductName).collect(Collectors.joining(", "));
		}
		log.info("combinedName: {}", combinedName);

		int totalPrice = cartItems.stream()
				.mapToInt(item -> item.getAmount() * products.stream()
						.filter(p -> p.getProductId() == item.getProductId()).findFirst().get().getProductPrice())
				.sum();

		return new SendToPayDTO(combinedName, totalPrice);
	}

	@Override
	public List<ProductsDTO> findProductByCartId(int cartId) {
		List<CartItemsDTO> cartItems = cartItemsService.findByCartId(cartId);

		List<ProductsDTO> products = cartItems.stream().map(item -> productsService.findByProductId(item.getProductId()))
				.collect(Collectors.toList());
		log.info("dto에 담긴것은?"+products);
		return products ; 
	
	}
}
