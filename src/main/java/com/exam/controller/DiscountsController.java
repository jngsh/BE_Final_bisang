package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DiscountProductDTO;
import com.exam.dto.DiscountsDTO;
import com.exam.service.DiscountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DiscountsController {

	DiscountsService discountsService;

    public DiscountsController(DiscountsService discountsService) {
		this.discountsService = discountsService;
	}

    @GetMapping("/home/discounts")
    public List<DiscountProductDTO> findDiscountProduct() {
    	List<DiscountProductDTO> list = discountsService.findDiscountProduct();
    	return list;
    }
    
    @GetMapping("/home/featured")
    public List<DiscountProductDTO> findProductsPetType() {
    	List<DiscountProductDTO> list = discountsService.findProductsPetType();
    	return list;
    }
    
    @GetMapping("/home/top-selling-products")
    public List<DiscountProductDTO> sortProductsBySalesPrice() {
    	List<DiscountProductDTO> list = discountsService.sortProductsBySalesPrice();
    	return list;
    }

    @GetMapping("/home/discounts-test")
    public List<DiscountsDTO> discountProductJoin() {
    	List<DiscountsDTO> list = discountsService.discountProductJoin();
    	return list;
    }
    
}