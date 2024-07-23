package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;
import com.exam.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@RequestMapping("/admin")
public class SalesController {

	SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/admin/daily")
    public List<DailySalesDTO> findDailySales() {
    	log.info("logger: daily: {}", salesService.findDailySales());
        List<DailySalesDTO> list = salesService.findDailySales();
		return list;
    }

    @GetMapping("/admin/monthly")
    public List<MonthlySalesDTO> findMonthlySales() {
    	List<MonthlySalesDTO> list = salesService.findMonthlySales();
        return list;
    }

    @GetMapping("/admin/yearly")
    public List<YearlySalesDTO> findYearlySales() {
    	List<YearlySalesDTO> list = salesService.findYearlySales();
        return list;
    }
}