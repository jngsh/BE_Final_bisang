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
    LocalDate createDate;
}
