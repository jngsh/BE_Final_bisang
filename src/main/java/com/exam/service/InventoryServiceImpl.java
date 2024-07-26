package com.exam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Inventory;
import com.exam.entity.Products;
import com.exam.repository.InventoryRepository;
import com.exam.repository.ProductsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	ProductsRepository productsRepository;
	
	@Override
	public void upsertInventory(Inventory inventory) {
        Optional<Inventory> existingInventoryOpt = inventoryRepository.findFirstByProductId(inventory.getProductId());

        if (existingInventoryOpt.isPresent()) {
            Inventory existingInventory = existingInventoryOpt.get();
            // Update existing inventory only if stock quantity is different
            if (!existingInventory.getStockQuantity().equals(inventory.getStockQuantity())) {
                existingInventory.setStockQuantity(inventory.getStockQuantity());
                existingInventory.setLastUpdated(LocalDateTime.now());
                inventoryRepository.save(existingInventory);
            }
        } else {
            // Insert new inventory
            inventory.setLastUpdated(LocalDateTime.now());
            inventoryRepository.save(inventory);
        }
    }

	@Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }
	
	@Override
    public List<Products> findAllProducts() {
        return productsRepository.findAll();
    }


}
