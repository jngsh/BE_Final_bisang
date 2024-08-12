package com.exam.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("OrderDetailsProductsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderDetailsProductsDTO {

	Integer orderDetailId;
	Integer orderId;
	Integer productId;
	Integer amount;
	Integer totalPrice;
	String productName;
	Integer productPrice;
	String productImage;

}