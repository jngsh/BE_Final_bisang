package com.exam.config;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.SalesStatsDTO;

@Mapper
public interface SalesMapper {

	public List<SalesStatsDTO> findRecentWeekSales();
	public List<SalesStatsDTO> findHourlySalesByDate(LocalDate date);
	public List<SalesStatsDTO> findDailySalesByMonth(int year, int month);
	public List<SalesStatsDTO> findMonthlySalesByYear(int year);
	public List<SalesStatsDTO> findYearlySales();
	public List<Integer> findYears();

//	public List<HourlySalesDTO> findHourlySales();
//	public List<DailySalesDTO> findDailySales();
//	public List<WeeklySalesDTO> findWeeklySales();
//	public List<MonthlySalesDTO> findMonthlySales();
//	public HourlySalesDTO findSalesByHour(int hour);
//	public DailySalesDTO findSalesByDate(LocalDate date);
//	public MonthlySalesDTO findSalesByMonth(int year, int month);
//	public YearlySalesDTO findSalesByYear(int year);
	
}
