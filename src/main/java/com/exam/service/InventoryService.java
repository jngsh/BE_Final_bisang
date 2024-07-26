package com.exam.service;

import java.util.List;

import com.exam.entity.Inventory;
import com.exam.entity.Products;

public interface InventoryService {

	void upsertInventory(Inventory inventory);
	List<Inventory> findAll();
	
	List<Products> findAllProducts();
}
