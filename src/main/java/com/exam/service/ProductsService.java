package com.exam.service;

import java.util.List;
import java.util.Map;

import com.exam.dto.DiscountsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;

public interface ProductsService {
    
	// 상품 ID로 상품 조회
	ProductsDTO findByProductId(int productId);
    // 모든 상품 조회
	List<ProductsDTO> findAllProducts();
	
	public void insertProducts(Products products);
	
	public String findCategoryCode(int categoryId);
	public Integer findCategoryIdByCode(Map<String, Object> type);
	
	public List<DiscountsDTO> searchProducts(String query);
	
    public List<String> suggestKeywords(String query);
	
}
