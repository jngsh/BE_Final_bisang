package com.exam.dto;

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
public class OrderDetailsDTO {
	
	int orderDetailId;
	int productId;
	int orderId;
	int amount;
	int totalPrice;
   
     String productName;
     int productPrice;
     String productImage;	

}
