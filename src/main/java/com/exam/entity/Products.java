package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Column;
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
   @Column(name = "product_id", nullable = false)
   Integer productId;
   
   @Column(name = "category_id", nullable = false)
   Integer categoryId;
   
   @Column(name = "discount_id", nullable = false)
   Integer discountId;
   
   @Column(name = "product_name")
   String productName;
   
   @Column(name = "product_price")
   Integer productPrice;
   
   @Column(name = "product_image")
   String productImage;
   
   @Column(name = "product_description")
   String productDescription;
   
   @Column(name = "unit")
   String unit;
   
   @Column(name = "value")
   Double value;
   
   @Column(name = "product_qr")
   String productQr;
   
   @Column(name = "product_code")
   String productCode;
   
   @Column(name = "created_date")
   LocalDate createdDate;
}
