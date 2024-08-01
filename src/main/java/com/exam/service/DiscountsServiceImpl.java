package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.config.DiscountsMapper;
import com.exam.dto.DiscountProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DiscountsServiceImpl implements DiscountsService {
	
	DiscountsMapper discountsMapper;

	public DiscountsServiceImpl(DiscountsMapper discountsMapper) {
		this.discountsMapper = discountsMapper;
	}

	@Override
	public List<DiscountProductDTO> findDiscountProduct(int discountId) {
		List<DiscountProductDTO> result = discountsMapper.findDiscountProduct(discountId);
		return result;
	}

	@Override
	public List<DiscountProductDTO> findProductsPetType() {
		List<DiscountProductDTO> result = discountsMapper.findProductsPetType();
		return result;
	}

	@Override
	public List<DiscountProductDTO> SortProductsBySalesPrice() {
		List<DiscountProductDTO> result = discountsMapper.SortProductsBySalesPrice();
		return result;
	}
	
	

}
