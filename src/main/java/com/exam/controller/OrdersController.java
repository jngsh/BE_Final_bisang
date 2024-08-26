package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.OrderDetailsDTO;
import com.exam.dto.OrdersAccountDTO;
import com.exam.service.OrderDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // Controller이면 뷰인줄알고 에러띄움
@RequestMapping("/orders")
public class OrdersController {

	
	OrderDetailsService orderDetailsService;
	
	
	
	public OrdersController(OrderDetailsService orderDetailsService) {
		this.orderDetailsService = orderDetailsService;
	}



	@GetMapping("/{userId}")
	public List<OrdersAccountDTO> FindOrdersAndDetails(@PathVariable int userId) {
        List<OrdersAccountDTO> orders = orderDetailsService.FindOrdersAndDetails(userId);
        return orders;
    }
	
	@GetMapping("/details/{orderId}")
	public List<OrderDetailsDTO> findOrderDetailsProducts(@PathVariable int orderId) {
        List<OrderDetailsDTO> orderDetails = orderDetailsService.findOrderDetailsProducts(orderId);
        return orderDetails;
    }
	
}

