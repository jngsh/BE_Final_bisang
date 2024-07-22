package com.exam.dto;

import java.time.LocalDate;

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
	
	int order_detail_id;
	int product_id;
	int order_id;
	int amount;
	int discount;
	

}
