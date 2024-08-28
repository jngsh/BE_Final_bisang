package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "discounts")
public class Discounts {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int discount_id;
   
   String discount_type;
   double discount_rate;
   LocalDate start_date;
   LocalDate end_date;
}
