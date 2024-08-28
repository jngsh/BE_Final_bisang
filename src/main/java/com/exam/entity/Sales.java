package com.exam.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "sales")
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int saleId;
	int orderId;
	int productId;
	int saleAmount;
	int salePrice;
	LocalDateTime saleDate;
	
	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	Products products;

	@ManyToOne
	@JoinColumn(name = "orderId", insertable = false, updatable = false)
	Orders orders;
}