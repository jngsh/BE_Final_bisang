package com.exam.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.config.ProductsMapper;
import com.exam.dto.DiscountsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import com.exam.repository.ProductsRepository;
import com.exam.util.KoreanTextProcessor;

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
	        productsRepository.save(products);
	    } catch (Exception e) {
	        log.error("insertProducts 실패: {}", e.getMessage());
	        throw e;
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
	
	@Override
	public List<DiscountsDTO> searchProducts(String query) {
		List<DiscountsDTO> list = productsMapper.searchProducts(query);
		return list;
	}
	
	@Override
	public List<String> suggestKeywords(String query) {
	    List<ProductsDTO> products = productsMapper.getSuggestedProducts(query);
	    Set<String> allNgrams = new HashSet<>();

	    for (ProductsDTO product : products) {

	        List<String> processedNameNgrams1 = KoreanTextProcessor.removeParticles(generateNgrams(product.getProductName(), 1));
	        List<String> processedNameNgrams2 = KoreanTextProcessor.removeParticles(generateNgrams(product.getProductName(), 2));
	        List<String> processedDescriptionNgrams2 = KoreanTextProcessor.removeParticles(generateNgrams(product.getProductDescription(), 2));

	        allNgrams.addAll(processedNameNgrams1);
	        allNgrams.addAll(processedNameNgrams2);
	        allNgrams.addAll(processedDescriptionNgrams2);
	    }

	    List<String> filteredKeywords = allNgrams.stream()
	    										 .filter(keyword -> keyword.contains(query))
	    										 .distinct()
	    										 .sorted(Comparator.comparingInt(String::length))
	    										 .collect(Collectors.toList());
	    return filteredKeywords;
	}


    private List<String> generateNgrams(String text, int n) {
        List<String> ngrams = new ArrayList<>();
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - n + 1; i++) {
            ngrams.add(String.join(" ", Arrays.copyOfRange(words, i, i + n)));
        }
        return ngrams;
    }

}
