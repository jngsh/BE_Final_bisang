package com.exam.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exam.config.CategoriesMapper;
import com.exam.dto.DiscountsDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	CategoriesMapper categoriesMapper;
	
	public CategoriesServiceImpl(CategoriesMapper categoriesMapper) {
		this.categoriesMapper = categoriesMapper;
	}
	
	@Override
	public List<Map<String, Object>> findItemCategory() {
		List<Map<String, Object>> result = categoriesMapper.findItemCategory();
		return result;
	}

	@Override
	public List<Map<String, Object>> findCategoryByPetType(String petType) {
		List<Map<String, Object>> result = categoriesMapper.findCategoryByPetType(petType);
		return result;
	}

	@Override
	public List<DiscountsDTO> findAllCategoryProducts() {
		List<DiscountsDTO> result = categoriesMapper.findAllCategoryProducts();
		return result;
	}

}
