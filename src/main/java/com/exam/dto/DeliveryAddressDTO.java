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
public class DeliveryAddressDTO {
	
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
