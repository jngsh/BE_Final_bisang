package com.exam.dto;

import java.time.LocalDate;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("SalesStatsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SalesStatsDTO {

	int totalSale;
	int saleHour;
	LocalDate saleDate;
	int saleMonth;
	int saleYear;
	
	int productId;
	int productSalesAmount;
	int productSalesPrice;
}
