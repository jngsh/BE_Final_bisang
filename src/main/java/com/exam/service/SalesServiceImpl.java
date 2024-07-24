package com.exam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exam.config.SalesMapper;
import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourSalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {
	
	SalesMapper salesMapper;
	
	public SalesServiceImpl(SalesMapper salesMapper) {
//		log.info("logger: SalesServiceImpl");
		this.salesMapper = salesMapper;
	}
	
	@Override
	public List<HourSalesDTO> findHourSales() {
		List<HourSalesDTO> result = salesMapper.findHourSales();
//		log.info("logger: hour result: {}", result);
		return result;
	}

	@Override
	public List<DailySalesDTO> findDailySales() {
		List<DailySalesDTO> result = salesMapper.findDailySales();
//		log.info("logger: daily result: {}", result);
		return result;
	}

	@Override
	public List<MonthlySalesDTO> findMonthlySales() {
		List<MonthlySalesDTO> result = salesMapper.findMonthlySales();
//		log.info("logger: monthly result: {}", result);
		return result;
	}

	@Override
	public List<YearlySalesDTO> findYearlySales() {
		List<YearlySalesDTO> result = salesMapper.findYearlySales();
//		log.info("logger: yearly result: {}", result);
		return result;
	}

	@Override
	public HourSalesDTO findSalesByHour(int hour) {
		HourSalesDTO result = salesMapper.findSalesByHour(hour);
		return result;
	}

	@Override
	public DailySalesDTO findSalesByDaily(LocalDate date) {
		DailySalesDTO result = salesMapper.findSalesByDaily(date);
		return result;
	}

	@Override
	public MonthlySalesDTO findSalesByMonthly(int year, int month) {
		MonthlySalesDTO result = salesMapper.findSalesByMonthly(year, month);
		return result;
	}

	@Override
	public YearlySalesDTO findSalesByYearly(int year) {
		YearlySalesDTO result = salesMapper.findSalesByYearly(year);
		return result;
	}

}
