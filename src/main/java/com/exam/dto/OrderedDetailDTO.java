package com.exam.dto;

import java.time.LocalDate;
//필요없을듯!
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//안쓰는ㄴ듯
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
	int cartId;
    int amount;

}
