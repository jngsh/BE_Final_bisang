package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;

@Mapper
public interface SalesMapper {

	public List<DailySalesDTO> findDailySales();
	public List<MonthlySalesDTO> findMonthlySales();
	public List<YearlySalesDTO> findYearlySales();
}
