package com.exam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.exam.dto.ProductsDTO;
import com.exam.service.ProductsService;

@RestController
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	ProductsService productsService;

	public ProductController(ProductsService productsService) {
		this.productsService = productsService;
	}
	
	// 현아 부분
	@GetMapping("/products/{productId}")
	public ProductsDTO findByProductId(@PathVariable int productId) {
		logger.info("logger: Finding product with ID: {}", productId);
		return productsService.findByProductId(productId);
	}
	
}
