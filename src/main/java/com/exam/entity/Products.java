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
   @Column(name = "product_id")
   int productId;
   @Column(name = "category_id")
   int categoryId;
   @Column(name = "discount_id")
   int discountId;
   @Column(name = "product_name")
   String productName;
   @Column(name = "product_price")
   int productPrice;
   @Column(name = "product_image")
   String productImage;
   @Column(name = "product_description")
   String productDescription;
   
   String unit;
   double value;
   @Column(name = "product_qr")
   String productQr;
   @Column(name = "product_code")
   String productCode;
   @Column(name = "created_date")
   LocalDate createdDate;
   @Column(name = "product_detailed_description")
   String productDetailedDescription;
   @Column(name = "product_additional_info")
   String productAdditionalInfo;
}
