package com.exam.service;

import java.util.List;
import java.util.Map;

import com.exam.dto.DiscountsDTO;

public interface CategoriesService {

	public List<Map<String, Object>> findItemCategory();
	public List<Map<String, Object>> findCategoryByPetType(String petType);
	public List<DiscountsDTO> findAllCategoryProducts();
}
