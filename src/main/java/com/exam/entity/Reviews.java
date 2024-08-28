package com.exam.entity;

import java.time.LocalDate;

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
	
	String contents;
	String reviewImage;
	Byte rating;
	
	@Builder.Default
	LocalDate reviewDate = LocalDate.now();
	
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
