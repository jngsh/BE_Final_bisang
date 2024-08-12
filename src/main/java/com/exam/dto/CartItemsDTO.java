package com.exam.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.exam.entity.Products;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("CartItemsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Getter
//@Setter
@ToString
public class CartItemsDTO {
	
	int cartItemId;
    int cartId;
    int productId;
    int amount;
    boolean shipping;
    
    ProductsDTO product;

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isShipping() {
		System.out.println("getShipping");
		return shipping;
	}

	public void setShipping(boolean shipping) {
		System.out.println("setShipping");
		this.shipping = shipping;
	}

	public ProductsDTO getProduct() {
		return product;
	}

	public void setProduct(ProductsDTO product) {
		this.product = product;
	}

   
}
