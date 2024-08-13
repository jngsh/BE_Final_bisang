package com.exam.service;

import java.util.List;

import com.exam.dto.DiscountProductDTO;
import com.exam.dto.DiscountsDTO;

public interface DiscountsService {
	
	public List<DiscountProductDTO> findDiscountProduct();
	public List<DiscountProductDTO> findProductsPetType();
	public List<DiscountProductDTO> sortProductsBySalesPrice();
	
	public List<DiscountsDTO> discountProductJoin();
	
}
