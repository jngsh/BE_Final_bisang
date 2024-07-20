package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
public class Discount {
   
   @Id
   int discount_id;
   
   String discount_type;
   double discount_rate;
   LocalDate start_date;
   LocalDate end_date;
   LocalDate discount_date;
}
