package com.exam.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourSalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

@Mapper
public interface SalesMapper {

	public List<HourSalesDTO> findHourSales();
	public List<DailySalesDTO> findDailySales();
	public List<MonthlySalesDTO> findMonthlySales();
	public List<YearlySalesDTO> findYearlySales();
	public HourSalesDTO findSalesByHour(int hour);
	public DailySalesDTO findSalesByDaily(LocalDate date);
	public MonthlySalesDTO findSalesByMonthly(int year, int month);
	public YearlySalesDTO findSalesByYearly(int year);
	
}
