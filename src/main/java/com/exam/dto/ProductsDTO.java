package com.exam.dto;

import java.time.LocalDate;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("ProductsDTO")
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
   LocalDate createdDate;
   
}
