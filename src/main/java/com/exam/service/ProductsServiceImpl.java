package com.exam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.config.ProductsMapper;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import com.exam.repository.ProductsRepository;

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
}
