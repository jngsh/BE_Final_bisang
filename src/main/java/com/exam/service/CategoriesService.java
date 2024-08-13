package com.exam.service;

import java.util.List;
import java.util.Map;

import com.exam.dto.DiscountsDTO;
import com.exam.dto.ItemCategoryDTO;

public interface CategoriesService {

	public List<ItemCategoryDTO> findItemCategory();
	
//	public List<Map<String, Object>> findCategories();
	public List<Map<String, Object>> findCategoryByPetType(String petType);
	
	public List<DiscountsDTO> findProductsByCategory(Map<String, Object> type);
}
