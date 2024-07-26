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
public class ProductsDTO {

	Integer productId;
	Integer categoryId;
	Integer discountId;
	String productName;
	Integer productPrice;
	String productImage;
	String productDescription;
	String unit;
	Double value;
	String productQr;
	String productCode;
	LocalDate createdDate;

}
