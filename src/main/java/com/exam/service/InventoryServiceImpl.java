package com.exam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.config.StocksMapper;
import com.exam.dto.InventoryDTO;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Inventory;
import com.exam.repository.InventoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;

	StocksMapper stocksMapper;

	public InventoryServiceImpl(StocksMapper stocksMapper) {
		this.stocksMapper = stocksMapper;
	}

	@Override
	public void upsertInventory(Inventory inventory) {
		Optional<Inventory> existingInventoryOpt = inventoryRepository.findFirstByProductId(inventory.getProductId());

		if (existingInventoryOpt.isPresent()) {
			Inventory existingInventory = existingInventoryOpt.get();
			if (!existingInventory.getStockQuantity().equals(inventory.getStockQuantity())) {
				existingInventory.setStockQuantity(inventory.getStockQuantity());
				existingInventory.setLastUpdated(LocalDateTime.now());
				inventoryRepository.save(existingInventory);
			}
		} else {
			inventory.setLastUpdated(LocalDateTime.now());
			inventoryRepository.save(inventory);
		}
	}

	@Override
	public List<InventoryDTO> findAllStocks() {
		List<InventoryDTO> result = stocksMapper.findAllStocks();
		return result;
	}

	@Override
	public List<ProductsDTO> findAllProducts() {
		List<ProductsDTO> result = stocksMapper.findAllProducts();
		return result;
	}

}
