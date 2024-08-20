package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class Reviews {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int reviewId;
	
//	int product_id;
//	int order_id;
	String contents;
	String reviewImage;
	Byte rating;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	Products products;
	
	@OneToOne
	@JoinColumn(name= "orderDetailId")
	OrderDetails orderDetails;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	Orders orders;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	Users users;
}
