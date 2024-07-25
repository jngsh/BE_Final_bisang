package com.exam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.config.SalesMapper;
import com.exam.dto.SalesStatsDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {
	
	SalesMapper salesMapper;
	
	public SalesServiceImpl(SalesMapper salesMapper) {
		this.salesMapper = salesMapper;
	}
	
	@Override
	public List<SalesStatsDTO> findRecentWeekSales() {
		List<SalesStatsDTO> result = salesMapper.findRecentWeekSales();
		return result;
	}
	
	@Override
	public List<SalesStatsDTO> findHourlySalesByDate(LocalDate date) {
		List<SalesStatsDTO> result = salesMapper.findHourlySalesByDate(date);
		return result;
	}
	
	@Override
	public List<SalesStatsDTO> findDailySalesByMonth(int year, int month) {
		List<SalesStatsDTO> result = salesMapper.findDailySalesByMonth(year, month);
		return result;
	}

	@Override
	public List<SalesStatsDTO> findMonthlySalesByYear(int year) {
		List<SalesStatsDTO> result = salesMapper.findMonthlySalesByYear(year);
		return result;
	}
	
	@Override
	public List<SalesStatsDTO> findYearlySales() {
		List<SalesStatsDTO> result = salesMapper.findYearlySales();
		return result;
	}
	
	@Override
	public List<Integer> findYears() {
		List<Integer> result = salesMapper.findYears();
		return result;
	}

	
	
//	@Override
//	public List<HourlySalesDTO> findHourlySales() {
//		List<HourlySalesDTO> result = salesMapper.findHourlySales();
////		log.info("logger: hourly result: {}", result);
//		return result;
//	}
//
//	@Override
//	public List<DailySalesDTO> findDailySales() {
//		List<DailySalesDTO> result = salesMapper.findDailySales();
////		log.info("logger: daily result: {}", result);
//		return result;
//	}
//	
//	@Override
//	public List<WeeklySalesDTO> findWeeklySales() {
//		List<WeeklySalesDTO> result = salesMapper.findWeeklySales();
////		log.info("logger: weekly result: {}", result);
//		return result;
//	}
//
//	@Override
//	public List<MonthlySalesDTO> findMonthlySales() {
//		List<MonthlySalesDTO> result = salesMapper.findMonthlySales();
////		log.info("logger: monthly result: {}", result);
//		return result;
//	}
//
//	@Override
//	public HourlySalesDTO findSalesByHour(int hour) {
//		HourlySalesDTO result = salesMapper.findSalesByHour(hour);
//		return result;
//	}
//
//	@Override
//	public DailySalesDTO findSalesByDate(LocalDate date) {
//		DailySalesDTO result = salesMapper.findSalesByDate(date);
//		return result;
//	}
//
//	@Override
//	public MonthlySalesDTO findSalesByMonth(int year, int month) {
//		MonthlySalesDTO result = salesMapper.findSalesByMonth(year, month);
//		return result;
//	}
//
//	@Override
//	public YearlySalesDTO findSalesByYear(int year) {
//		YearlySalesDTO result = salesMapper.findSalesByYear(year);
//		return result;
//	}

}
