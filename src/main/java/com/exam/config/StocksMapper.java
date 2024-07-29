package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.InventoryDTO;
import com.exam.dto.ProductsDTO;

@Mapper
public interface StocksMapper {

	public List<InventoryDTO> findAllStocks();
	public List<ProductsDTO> findAllProducts();
	
}
