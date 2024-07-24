package com.exam.dto;

import java.time.LocalDate;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("DailySalesDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DailySalesDTO {

	int totalSale;
	LocalDate saleDate;
	int saleHour;
}
