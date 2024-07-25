package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.SalesStatsDTO;
import com.exam.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/stats/sales")
//@PreAuthorize("hasRole('ADMIN')")
public class SalesController {

	SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }
    
    @GetMapping("/recent-week")
    public List<SalesStatsDTO> findRecentWeekSales() {
    	List<SalesStatsDTO> list = salesService.findRecentWeekSales();
    	return list;
    }
    
    @GetMapping("/hourly/{date}")
    public List<SalesStatsDTO> findHourlySalesByDate(@PathVariable LocalDate date) {
    	List<SalesStatsDTO> list = salesService.findHourlySalesByDate(date);
    	return list;
    }
    
    @GetMapping("/daily/{year}/{month}")
    public List<SalesStatsDTO> findDailySalesByMonth(@PathVariable int year, @PathVariable int month) {
    	List<SalesStatsDTO> list = salesService.findDailySalesByMonth(year, month);
    	return list;
    }
    
    @GetMapping("/monthly/{year}")
    public List<SalesStatsDTO> findMonthlySalesByYear(@PathVariable int year) {
    	List<SalesStatsDTO> list = salesService.findMonthlySalesByYear(year);
    	return list;
    }
    
    @GetMapping("/yearly")
    public List<SalesStatsDTO> findYearlySales() {
    	List<SalesStatsDTO> list = salesService.findYearlySales();
        return list;
    }
    
    @GetMapping("/years")
    public List<Integer> findYears() {
    	List<Integer> list = salesService.findYears();
        return list;
    }
    
    
    
    
//    @GetMapping("/admin/hourly")
//    public List<HourlySalesDTO> findHourlySales() {
//        List<HourlySalesDTO> list = salesService.findHourlySales();
//		return list;
//    }
//    
//    @GetMapping("/admin/daily")
//    public List<DailySalesDTO> findDailySales() {
//        List<DailySalesDTO> list = salesService.findDailySales();
//		return list;
//    }
//    
//    
//    @GetMapping("/admin/weekly")
//    public List<WeeklySalesDTO> findWeeklySales() {
//        List<WeeklySalesDTO> list = salesService.findWeeklySales();
//		return list;
//    }
//
//    @GetMapping("/admin/monthly")
//    public List<MonthlySalesDTO> findMonthlySales() {
//    	List<MonthlySalesDTO> list = salesService.findMonthlySales();
//        return list;
//    }
//
//    @GetMapping("/admin/hourly/{hour}")
//    public HourlySalesDTO findSalesByHour(@PathVariable int hour) {
//        HourlySalesDTO dto = salesService.findSalesByHour(hour);
//		return dto;
//    }
//    
//    @GetMapping("/admin/daily/{date}")
//    public DailySalesDTO findSalesByDate(@PathVariable LocalDate date) {
//    	DailySalesDTO dto = salesService.findSalesByDate(date);
//    	log.info("logger: findSalesByDate: {}", dto);
//		return dto;
//    }
//    
//    
//    @GetMapping("/admin/monthly/{year}/{month}")
//    public MonthlySalesDTO findSalesByMonth(@PathVariable int year, @PathVariable int month) {
//    	MonthlySalesDTO dto = salesService.findSalesByMonth(year, month);
//		return dto;
//    }
//    
//    @GetMapping("/admin/yearly/{year}")
//    public YearlySalesDTO findSalesByYear(@PathVariable int year) {
//    	YearlySalesDTO dto = salesService.findSalesByYear(year);
//		return dto;
//    }
}