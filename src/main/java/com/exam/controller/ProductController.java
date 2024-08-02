package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	// 현아, 솔 부분
	// productId로 조회 (JPA)
	@GetMapping("/products/{productId}")
	public ProductsDTO findByProductId(@PathVariable int productId) {
		logger.info("logger: Finding product with ID: {}", productId);
		return productsService.findByProductId(productId);
	}

	// 현아 부분
	// 상품 전체 조회 (mybatis)
	@GetMapping("/products")
	public List<ProductsDTO> findAllProducts() {
		return productsService.findAllProducts();
	}
	
}
