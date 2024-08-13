package com.exam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.config.OrderDetailsMapper;
import com.exam.dto.OrderDetailsDTO;
import com.exam.dto.OrderDetailsProductsDTO;
import com.exam.dto.OrdersDTO;
import com.exam.entity.OrderDetails;
import com.exam.entity.Orders;
import com.exam.repository.OrderDetailsRepository;
import com.exam.repository.OrdersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {

	OrderDetailsRepository orderDetailsRepository;
	OrdersRepository ordersRepository;
	OrderDetailsMapper orderDetailsMapper;

	public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, OrdersRepository ordersRepository,
			OrderDetailsMapper orderDetailsMapper) {
		this.orderDetailsRepository = orderDetailsRepository;
		this.ordersRepository = ordersRepository;
		this.orderDetailsMapper = orderDetailsMapper;
	}

	@Override
	public Orders findOrder(int cartId) {

		ModelMapper mapper = new ModelMapper();
		OrdersDTO dto = orderDetailsMapper.findOrder(cartId);
		dto.setNonMemberId(1);// 임의
		dto.setOrderDate(LocalDateTime.now());
		log.info("orders이부분?:{}", dto);
		// orderId : auto increment로 자동생성됨.
		Orders orders = mapper.map(dto, Orders.class);
		return ordersRepository.save(orders);
	}

	@Override
	public List<OrderDetails> saveAllOrderDetails(Orders savedOrder, List<OrderDetailsDTO> list) {
		int orderId = savedOrder.getOrderId();
		
		log.info("savedOrder 정상호출?: {}", savedOrder);

		ModelMapper mapper = new ModelMapper();

		for (OrderDetailsDTO dto : list) {
			dto.setTotalPrice(dto.getAmount() * dto.getTotalPrice());
			dto.setOrderId(orderId);
		}

		log.info("Order details DTO list: {}", list);

		List<OrderDetails> orderDetailsList = list.stream().map(dto -> mapper.map(dto, OrderDetails.class))
				.collect(Collectors.toList());

		return orderDetailsRepository.saveAll(orderDetailsList);

	}

	@Override
	public int saveOrderDetails(OrderDetailsDTO orderDetailsDTO) {
		ModelMapper mapper = new ModelMapper();
		OrderDetails orderDetails = mapper.map(orderDetailsDTO, OrderDetails.class);
		orderDetailsRepository.save(orderDetails);

		return orderDetails.getOrderDetailId();
	}
	

	@Override
	public OrderDetails findByOrderDetailId(Integer orderDetailId) {
		return orderDetailsRepository.findByOrderDetailId(orderDetailId);
	}

	@Override
	public List<OrderDetailsDTO> findOrderDetails(int cartId) {
		return orderDetailsMapper.findOrderDetails(cartId);
	}

	@Override
	public List<OrderDetailsDTO> findOrderDetailsProducts(int orderId) {
		return orderDetailsMapper.findOrderDetailsProducts(orderId);
	}

//	@Override
//	public List<OrderDetailsProductsDTO> findOrderDetailsWithProducts(Integer orderId) {
//        return orderDetailsMapper.findOrderDetailsProducts(orderId);
//	}

}