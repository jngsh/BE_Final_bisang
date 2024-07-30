package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.config.CategoriesMapper;
import com.exam.dto.ItemCategoryDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	CategoriesMapper categoriesMapper;
	
	public CategoriesServiceImpl(CategoriesMapper categoriesMapper) {
		this.categoriesMapper = categoriesMapper;
	}

	@Override
	public List<ItemCategoryDTO> findItemCategory() {
		List<ItemCategoryDTO> result = categoriesMapper.findItemCategory();
		return result;
	}

}
