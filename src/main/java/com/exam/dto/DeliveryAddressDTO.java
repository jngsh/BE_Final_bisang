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
	
	int deliveryAddressId;
	int userId;
	String addressType;
	String deliveryName;
	String address1;
	String address2;
	String post;
	String phone1;
	String phone2;
	String phone3;
	boolean isDefault;
	

}
