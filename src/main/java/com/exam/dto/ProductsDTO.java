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
   int category_id;
   int discount_id;
   String productName;
   int product_price;
   String productImage;
   String product_description;
   String unit;
   double value;
   String product_qr;
   String product_code;
   LocalDate product_date;
   
}
