package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourSalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.YearlySalesDTO;
import com.exam.service.SalesService;

@RestController
public class SalesController {

	SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }
    
    @GetMapping("/admin/hour")
    public List<HourSalesDTO> findHourSales() {
        List<HourSalesDTO> list = salesService.findHourSales();
		return list;
    }
    
    @GetMapping("/admin/daily")
    public List<DailySalesDTO> findDailySales() {
        List<DailySalesDTO> list = salesService.findDailySales();
		return list;
    }

    @CrossOrigin(origins = "http://localhost:3000")
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
    
    @GetMapping("/admin/hour/{hour}")
    public HourSalesDTO findSalesByHour(@PathVariable int hour) {
        HourSalesDTO dto = salesService.findSalesByHour(hour);
		return dto;
    }
    
    @GetMapping("/admin/daily/{date}")
    public DailySalesDTO findSalesByDaily(@PathVariable LocalDate date) {
    	DailySalesDTO dto = salesService.findSalesByDaily(date);
		return dto;
    }
    
    @GetMapping("/admin/monthly/{year}/{month}")
    public MonthlySalesDTO findSalesByMonthly(@PathVariable int year, @PathVariable int month) {
    	MonthlySalesDTO dto = salesService.findSalesByMonthly(year, month);
		return dto;
    }
    
    @GetMapping("/admin/yearly/{year}")
    public YearlySalesDTO findSalesByYearly(@PathVariable int year) {
    	YearlySalesDTO dto = salesService.findSalesByYearly(year);
		return dto;
    }
}