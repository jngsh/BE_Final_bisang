package com.exam.service;

import java.util.List;

import com.exam.entity.Inventory;

public interface InventoryService {

	void upsertInventory(Inventory inventory);
	List<Inventory> findAll();
}
