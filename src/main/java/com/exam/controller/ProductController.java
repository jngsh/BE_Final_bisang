package com.exam.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exam.service.ProductsService;

@RestController
public class ProductController {
	
	ProductsService productsService;

	public ProductController(ProductsService productsService) {
		this.productsService = productsService;
	}
	
}
