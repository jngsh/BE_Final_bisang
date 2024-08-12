package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.OrderDetails;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
	//productid, orderid, amount, totalprice
	
	public OrderDetails findByOrderDetailId(Integer orderDetailId);

	
	
	
}
