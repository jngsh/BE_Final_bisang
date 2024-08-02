package com.exam.service;

import java.util.List;

import com.exam.dto.ProductsDTO;

public interface ProductsService {
    
	// 상품 ID로 상품 조회
	ProductsDTO findByProductId(int productId);
    // 모든 상품 조회
	List<ProductsDTO> findAllProducts();
	
}
