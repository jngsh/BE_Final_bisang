package com.exam.dto;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("DiscountsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DiscountsDTO {

   int discountId;

   String discountType;
   double discountRate;
   LocalDate startDate;
   LocalDate endDate;
   
   List<ProductsDTO> productsList;
}