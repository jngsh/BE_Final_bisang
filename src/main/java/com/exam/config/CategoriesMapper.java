package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.ItemCategoryDTO;

@Mapper
public interface CategoriesMapper {

	public List<ItemCategoryDTO> findItemCategory();

}
