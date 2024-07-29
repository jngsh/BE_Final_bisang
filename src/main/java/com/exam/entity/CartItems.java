package com.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
	int cartItemId;
	int cartId;
//	@ManyToOne
//	@JoinColumn(name = "cart_id")
//	Carts cart;

	@Column(name = "product_id")
	int productId;
	int amount;

}
