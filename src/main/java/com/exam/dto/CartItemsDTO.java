package com.exam.dto;

import java.util.List;

import com.exam.entity.Products;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class CartItemsDTO {
	
	int cartItemId;
    int cartId;
    int productId;
    int amount;
    
    ProductsDTO product;
    @JsonIgnore
    CartsDTO cart;
 
    
}
