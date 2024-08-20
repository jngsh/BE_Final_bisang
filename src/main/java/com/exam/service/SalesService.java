package com.exam.service;

import java.time.LocalDate;
import java.util.List;

import com.exam.dto.PetsStatsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.dto.SalesStatsDTO;

public interface SalesService {

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
	
	public List<SalesStatsDTO> findAllDailySales();
}
