package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DiscountsDTO;
import com.exam.dto.ItemCategoryDTO;

@Mapper
public interface CategoriesMapper {

	public List<ItemCategoryDTO> findItemCategory();
	
//	public List<Map<String, Object>> findCategories();
	public List<Map<String, Object>> findCategoryByPetType(String petType);
	
	public List<DiscountsDTO> findProductsByCategory(Map<String, Object> type);

}
