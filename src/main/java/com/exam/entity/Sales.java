package com.exam.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@Entity
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int sale_id;
	int order_id;
	int product_id;
	int sale_amount;
	int sale_price;
	LocalDateTime sale_date;

}
