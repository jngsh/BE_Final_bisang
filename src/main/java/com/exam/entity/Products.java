package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Products {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
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
   LocalDate productDate;
}
