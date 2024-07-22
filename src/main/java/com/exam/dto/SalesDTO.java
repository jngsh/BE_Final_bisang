package com.exam.dto;

import java.time.LocalDateTime;

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
public class SalesDTO {

	int sale_id;
	int order_id;
	int product_id;
	int sale_amount;
	int sale_price;
	LocalDateTime sale_date;
}
