package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.OrderDetailsDTO;
import com.exam.dto.OrdersDTO;

@Mapper
public interface OrderDetailsMapper {

	public List<OrderDetailsDTO> findOrderDetails(int cartId);

	public OrdersDTO findOrder(int cartId);
	
//    List<CartItemsDTO> findcartItemsProducts(int cartId);
    public List<OrderDetailsDTO> findOrderDetailsProducts(int orderId);

}
