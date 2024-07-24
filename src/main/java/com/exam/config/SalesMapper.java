package com.exam.config;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourlySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.WeeklySalesDTO;
import com.exam.dto.YearlySalesDTO;

@Mapper
public interface SalesMapper {

	public List<HourlySalesDTO> findHourlySales();
	public List<DailySalesDTO> findDailySales();
	public List<WeeklySalesDTO> findWeeklySales();
	public List<MonthlySalesDTO> findMonthlySales();
	public List<YearlySalesDTO> findYearlySales();
	public HourlySalesDTO findSalesByHour(int hour);
	public DailySalesDTO findSalesByDate(LocalDate date);
	public MonthlySalesDTO findSalesByMonth(int year, int month);
	public YearlySalesDTO findSalesByYear(int year);
	
}
