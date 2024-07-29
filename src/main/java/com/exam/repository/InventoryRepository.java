package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
	
	Optional<Inventory> findFirstByProductId(Integer productId);
}
