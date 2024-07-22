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
public class PaymentsDTO {

	int payment_id;
	int order_id;
	int total_price;
	int discount_price;
	int delivery_fee;
	int final_price;
	LocalDateTime payment_date;
	int points;
}
