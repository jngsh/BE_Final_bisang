package com.exam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourSalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

public interface SalesService {

	public List<HourSalesDTO> findHourSales();
	public List<DailySalesDTO> findDailySales();
	public List<MonthlySalesDTO> findMonthlySales();
	public List<YearlySalesDTO> findYearlySales();
	public HourSalesDTO findSalesByHour(int hour);
	public DailySalesDTO findSalesByDaily(LocalDate date);
	public MonthlySalesDTO findSalesByMonthly(int year, int month);
	public YearlySalesDTO findSalesByYearly(int year);
}
