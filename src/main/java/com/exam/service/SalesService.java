package com.exam.service;

import java.time.LocalDate;
import java.util.List;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourlySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

public interface SalesService {

	public List<HourlySalesDTO> findHourlySales();
	public List<DailySalesDTO> findDailySales();
	public List<MonthlySalesDTO> findMonthlySales();
	public List<YearlySalesDTO> findYearlySales();
	public HourlySalesDTO findSalesByHour(int hour);
	public DailySalesDTO findSalesByDate(LocalDate date);
	public MonthlySalesDTO findSalesByMonth(int year, int month);
	public YearlySalesDTO findSalesByYear(int year);
}
