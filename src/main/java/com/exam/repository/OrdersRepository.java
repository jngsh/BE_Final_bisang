package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Orders;


public interface OrdersRepository extends JpaRepository<Orders, Integer> {
		
	
}
