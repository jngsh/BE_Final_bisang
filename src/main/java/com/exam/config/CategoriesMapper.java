package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DiscountsDTO;

@Mapper
public interface CategoriesMapper {

	public List<Map<String, Object>> findItemCategory();
	public List<Map<String, Object>> findCategoryByPetType(String petType);
	public List<DiscountsDTO> findAllCategoryProducts();

}
