package com.exam.entity;

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
public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int orderDetailId;
	int productId;
	int orderId;
	int amount;
	int totalPrice;

}
