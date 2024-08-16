package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class DeliveryAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int deliveryAddrId;
//	int userId;
	String addressType;
	String deliveryName;
	String address1;
	String address2;
	String post;
	String phone1;
	String phone2;
	String phone3;
	boolean isDefault;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	Users users;

}
