package com.exam.service;

import java.util.List;

import com.exam.dto.OrderDetailsDTO;
import com.exam.entity.OrderDetails;
import com.exam.entity.Orders;

public interface OrderDetailsService {

	public int saveOrderDetails(OrderDetailsDTO orderDetailsDTO);
	
	public OrderDetails findByOrderDetailId(Integer orderDetailId);
	
	
	public List<OrderDetailsDTO> findOrderDetails(int cartId);
	
	public Orders findOrder(int cartId);
	
	public List<OrderDetails> saveAllOrderDetails(Orders savedOrder, List<OrderDetailsDTO> list);
	//orderDetails랑 proudcts 조인할 것
	public List<OrderDetailsDTO> findOrderDetailsProducts(int orderId);
	
}
