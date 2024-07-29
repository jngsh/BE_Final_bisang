package com.exam.service;

import java.util.List;

import com.exam.dto.InventoryDTO;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Inventory;

public interface InventoryService {

	void upsertInventory(Inventory inventory);
	
	public List<InventoryDTO> findAllStocks();
	public List<ProductsDTO> findAllProducts();
}
