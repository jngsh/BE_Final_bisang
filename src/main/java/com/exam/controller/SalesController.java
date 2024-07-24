package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DailySalesDTO;
import com.exam.dto.HourlySalesDTO;
import com.exam.dto.MonthlySalesDTO;
import com.exam.dto.WeeklySalesDTO;
import com.exam.dto.YearlySalesDTO;
import com.exam.service.SalesService;

@RestController
public class SalesController {

	SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }
    
    @GetMapping("/admin/hourly")
    public List<HourlySalesDTO> findHourlySales() {
        List<HourlySalesDTO> list = salesService.findHourlySales();
		return list;
    }
    
    @GetMapping("/admin/daily")
    public List<DailySalesDTO> findDailySales() {
        List<DailySalesDTO> list = salesService.findDailySales();
		return list;
    }
    
    @GetMapping("/admin/weekly")
    public List<WeeklySalesDTO> findWeeklySales() {
        List<WeeklySalesDTO> list = salesService.findWeeklySales();
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
    
    @GetMapping("/admin/hourly/{hour}")
    public HourlySalesDTO findSalesByHour(@PathVariable int hour) {
        HourlySalesDTO dto = salesService.findSalesByHour(hour);
		return dto;
    }
    
    @GetMapping("/admin/daily/{date}")
    public DailySalesDTO findSalesByDate(@PathVariable LocalDate date) {
    	DailySalesDTO dto = salesService.findSalesByDate(date);
		return dto;
    }
    
    @GetMapping("/admin/monthly/{year}/{month}")
    public MonthlySalesDTO findSalesByMonth(@PathVariable int year, @PathVariable int month) {
    	MonthlySalesDTO dto = salesService.findSalesByMonth(year, month);
		return dto;
    }
    
    @GetMapping("/admin/yearly/{year}")
    public YearlySalesDTO findSalesByYear(@PathVariable int year) {
    	YearlySalesDTO dto = salesService.findSalesByYear(year);
		return dto;
    }
}