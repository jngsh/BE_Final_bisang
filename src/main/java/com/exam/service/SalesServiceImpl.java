package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.config.SalesMapper;
import com.exam.dto.DailySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {
	
	SalesMapper salesMapper;
	
	public SalesServiceImpl(SalesMapper salesMapper) {
		log.info("logger: SalesServiceImpl 생성자");
		this.salesMapper = salesMapper;
	}

	@Override
	public List<DailySalesDTO> findDailySales() {
		List<DailySalesDTO> result = salesMapper.findDailySales();
		log.info("logger: daily result: {}", result);
		return result;
	}

	@Override
	public List<MonthlySalesDTO> findMonthlySales() {
		List<MonthlySalesDTO> result = salesMapper.findMonthlySales();
		log.info("logger: monthly result: {}", result);
		return result;
	}

	@Override
	public List<YearlySalesDTO> findYearlySales() {
		List<YearlySalesDTO> result = salesMapper.findYearlySales();
		log.info("logger: yearly result: {}", result);
		return result;
	}
	
}
