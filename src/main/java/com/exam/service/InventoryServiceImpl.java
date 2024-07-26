package com.exam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Inventory;
import com.exam.repository.InventoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
	
	InventoryRepository inventoryRepository;
	
	public InventoryServiceImpl(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}

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
//	public void upsertInventory(Inventory inventory) {
//        Optional<Inventory> existingInventoryOpt = inventoryRepository.findFirstByProductId(inventory.getProductId());
//
//        if (existingInventoryOpt.isPresent()) {
//            Inventory existingInventory = existingInventoryOpt.get();
//            
//            // 여기에 이전 stock quantity에서 값이 변했는지 확인하는 명령어 입력
//            // 변경되지 않았으면 조건문 빠져나감
//            
//            existingInventory.setStockQuantity(inventory.getStockQuantity());
//            existingInventory.setLastUpdated(LocalDateTime.now());
//            inventoryRepository.save(existingInventory);
//        } else {
//            // Insert new inventory
//            inventory.setLastUpdated(LocalDateTime.now());
//            inventoryRepository.save(inventory);
//        }
//    }
	
	@Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

}
