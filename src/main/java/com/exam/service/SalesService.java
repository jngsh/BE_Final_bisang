package com.exam.service;

import java.util.List;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

public interface SalesService {

	public List<DailySalesDTO> findDailySales();
	public List<MonthlySalesDTO> findMonthlySales();
	public List<YearlySalesDTO> findYearlySales();
}
