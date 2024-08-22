package com.exam.dto;




import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrdersAccountDTO {
	
	int orderId;
	int userId;
	LocalDateTime orderDate;
	int totalPrice;
	
	//orderDetails리스트
	List<OrderDetailsDTO> orderDetails;
}
