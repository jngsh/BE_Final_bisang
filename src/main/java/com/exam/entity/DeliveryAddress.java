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
public class DeliveryAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int delivery_addr_id;
	int user_id;
	String address_type;
	String delivery_name;
	String address1;
	String address2;
	String post;
	String phone1;
	String phone2;
	String phone3;
	boolean is_default;

}
