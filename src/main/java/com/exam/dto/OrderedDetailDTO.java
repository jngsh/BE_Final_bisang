package com.exam.dto;

import java.time.LocalDate;

import org.apache.ibatis.type.Alias;

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
public class OrderedDetailDTO {

	//products에서 받아온것
	int productId;
	int categoryId;
	int discountId;
	String productName;
	int productPrice;
	String productImage;
	String productDescription;
	String unit;
	double value;
	String productQr;
	String productCode;
	LocalDate createdDate;
	
	//cartitems에서 받아온것
    int amount;

}
