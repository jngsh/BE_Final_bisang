package com.exam.entity;

import javax.persistence.Entity;
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
public class DeliveryAddr {
	
	@Id
	int delivery_addr_id;
	int customer_id;
	String addr_type;
	String delivery_name;
	String addr1;
	String addr2;
	String post;
	String phone1;
	String phone2;
	String phone3;
	boolean is_default;

}
