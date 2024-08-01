package com.exam.service;

import java.util.List;

import com.exam.dto.DiscountProductDTO;

public interface DiscountsService {
	
	public List<DiscountProductDTO> findDiscountProduct(int discountId);
	public List<DiscountProductDTO> findProductsPetType();
	public List<DiscountProductDTO> SortProductsBySalesPrice();
	
}
