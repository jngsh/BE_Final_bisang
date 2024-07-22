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
   int product_id;
   int category_id;
   int discount_id;
   String product_name;
   int product_price;
   String product_image;
   String product_description;
   String unit;
   double value;
   String product_qr;
   String product_code;
   LocalDate product_date;
   
}
