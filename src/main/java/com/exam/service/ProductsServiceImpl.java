package com.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.config.ProductsMapper;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import com.exam.repository.ProductsRepository;

@Service
public class ProductsServiceImpl implements ProductsService {

    private ProductsRepository productsRepository;
    private ModelMapper modelMapper;
    
    @Autowired
    ProductsMapper productsMapper;

	public ProductsServiceImpl(ProductsRepository productsRepository, ModelMapper modelMapper) {
		this.productsRepository = productsRepository;
		this.modelMapper = modelMapper;
	}

	// R 이니까 mybatis로 바꿔야되나?
	@Override
		public ProductsDTO findByProductId(int productId) {
	        Products product = productsRepository.findByProductId(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	        return modelMapper.map(product, ProductsDTO.class);
	    }
}
