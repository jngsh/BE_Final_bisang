package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DiscountProductDTO;
import com.exam.service.DiscountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DiscountsController {

	DiscountsService discountsService;

    public DiscountsController(DiscountsService discountsService) {
		this.discountsService = discountsService;
	}

    @GetMapping("/home/discounts/{discountId}")
    public List<DiscountProductDTO> findDiscountProduct(@PathVariable("discountId") int discountId) {
    	List<DiscountProductDTO> list = discountsService.findDiscountProduct(discountId);
    	return list;
    }
    
    @GetMapping("/home/featured")
    public List<DiscountProductDTO> findProductsPetType() {
    	List<DiscountProductDTO> list = discountsService.findProductsPetType();
    	return list;
    }
    
    @GetMapping("/home/top-selling-products")
    public List<DiscountProductDTO> SortProductsBySalesPrice() {
    	List<DiscountProductDTO> list = discountsService.SortProductsBySalesPrice();
    	return list;
    }

    
}