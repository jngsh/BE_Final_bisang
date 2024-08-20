package com.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.config.ProductsMapper;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import com.exam.repository.ProductsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductsServiceImpl implements ProductsService {

    // mybatis 사용
    ProductsMapper productsMapper;

	// JPA 사용
	private ProductsRepository productsRepository;
	private ModelMapper modelMapper;
	
	// 생성자 하나로 통일하긔! (bean 어쩌구 오류남)
	public ProductsServiceImpl(ProductsRepository productsRepository, ModelMapper modelMapper, ProductsMapper productsMapper) {
		this.productsRepository = productsRepository;
		this.modelMapper = modelMapper;
		this.productsMapper = productsMapper;
	}

	// R 이니까 mybatis로 바꿔야되나?
	@Override
		public ProductsDTO findByProductId(int productId) {
	        Products product = productsRepository.findByProductId(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	        return modelMapper.map(product, ProductsDTO.class);
	    }

	@Override
	public List<ProductsDTO> findAllProducts() {
		return productsMapper.findAllProducts();
	}
	
	@Override
	public Products findByProductsId(int productId) {
		return productsRepository.findById(productId).orElse(null);
	}

	@Transactional
	@Override
	public void insertProducts(Products products) {

		try {
	        log.info("insertProducts 시작: {}", products);
	        productsRepository.save(products);
	        log.info("insertProducts 성공: {}", products);
	    } catch (Exception e) {
	        log.error("insertProducts 실패: {}", e.getMessage());
	        throw e; // 예외를 다시 던져 트랜잭션이 롤백되도록 합니다.
	    }
	}
	

	@Override
	public String findCategoryCode(int categoryId) {
		String categoryCode = productsMapper.findCategoryCode(categoryId);
		return categoryCode;
	}

	@Override
	public Integer findCategoryIdByCode(Map<String, Object> type) {
		Integer result = productsMapper.findCategoryIdByCode(type);
		return result;
	}

}
