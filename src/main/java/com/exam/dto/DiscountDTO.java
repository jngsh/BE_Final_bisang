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
public class DiscountDTO {

   int discount_id;

   String discount_type;
   double discount_rate;
   LocalDate start_date;
   LocalDate end_date;
   LocalDate discount_date;
}