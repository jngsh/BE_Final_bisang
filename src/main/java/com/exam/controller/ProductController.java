package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.ProductsDTO;
import com.exam.service.ProductsService;

@RestController
public class ProductController {
	
	ProductsService productsService;

	public ProductController(ProductsService productsService) {
		this.productsService = productsService;
	}
	
	@GetMapping("/home/top-selling-products")
    public List<ProductsDTO> SortProductsBySalesPrice() {
    	List<ProductsDTO> list = productsService.SortProductsBySalesPrice();
    	return list;
    }
	
	@GetMapping("/home/featured")
    public List<ProductsDTO> findProductsPetType() {
    	List<ProductsDTO> list = productsService.findProductsPetType();
    	return list;
    }

}
