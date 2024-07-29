package com.exam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.config.SalesMapper;
import com.exam.dto.PetsStatsDTO;
import com.exam.dto.ProductsDTO;
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

	@Override
	public List<SalesStatsDTO> findProductSalesPrice() {
		List<SalesStatsDTO> result = salesMapper.findProductSalesPrice();
		return result;
	}

	@Override
	public List<SalesStatsDTO> findProductSalesAmount() {
		List<SalesStatsDTO> result = salesMapper.findProductSalesAmount();
		return result;
	}


	@Override
	public List<ProductsDTO> findProductInfo() {
		List<ProductsDTO> result = salesMapper.findProductInfo();
		return result;
	}

	@Override
	public List<PetsStatsDTO> calcPetTypeRatio() {
		List<PetsStatsDTO> result = salesMapper.calcPetTypeRatio();
		return result;
	}

	@Override
	public List<PetsStatsDTO> calcPetAgeTypeRatio() {
		List<PetsStatsDTO> result = salesMapper.calcPetAgeTypeRatio();
		return result;
	}


}
