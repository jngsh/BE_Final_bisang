package com.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import com.exam.repository.ProductsRepository;

@Service
public class ProductsServiceImpl implements ProductsService {

    private ProductsRepository productsRepository;
    private ModelMapper modelMapper;

	public ProductsServiceImpl(ProductsRepository productsRepository, ModelMapper modelMapper) {
		this.productsRepository = productsRepository;
		this.modelMapper = modelMapper;
	}


	@Override
		public ProductsDTO getProductById(int productId) {
	        Products product = productsRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	        return modelMapper.map(product, ProductsDTO.class);
	    }

}
