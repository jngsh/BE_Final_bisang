package com.exam.config;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.PetsStatsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.dto.SalesStatsDTO;

@Mapper
public interface SalesMapper {

	public List<SalesStatsDTO> findRecentWeekSales();
	public List<SalesStatsDTO> findHourlySalesByDate(LocalDate date);
	public List<SalesStatsDTO> findDailySalesByMonth(int year, int month);
	public List<SalesStatsDTO> findMonthlySalesByYear(int year);
	public List<SalesStatsDTO> findYearlySales();
	public List<Integer> findYears();

	public List<SalesStatsDTO> findProductSalesPrice();
	public List<SalesStatsDTO> findProductSalesAmount();
	
	public List<ProductsDTO> findProductInfo();
	
	public List<PetsStatsDTO> calcPetTypeRatio();
	public List<PetsStatsDTO> calcPetAgeTypeRatio();

}
