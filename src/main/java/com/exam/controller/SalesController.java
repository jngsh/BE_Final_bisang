package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.PetsStatsDTO;
import com.exam.dto.ProductsDTO;
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
    
    @GetMapping("/sort-price")
    public List<SalesStatsDTO> findProductSalesPrice() {
    	List<SalesStatsDTO> list = salesService.findProductSalesPrice();
        return list;
    }
    
    @GetMapping("/sort-amount")
    public List<SalesStatsDTO> findProductSalesAmount() {
    	List<SalesStatsDTO> list = salesService.findProductSalesAmount();
        return list;
    }
    
    
    
    @GetMapping("/products-info")
    public List<ProductsDTO> findProductInfo() {
    	List<ProductsDTO> list = salesService.findProductInfo();
        return list;
    }
    
    @GetMapping("/pet-type")
    public List<PetsStatsDTO> calcPetTypeRatio() {
    	List<PetsStatsDTO> list = salesService.calcPetTypeRatio();
        return list;
    }
    
    @GetMapping("/pet-age-type")
    public List<PetsStatsDTO> calcPetAgeTypeRatio() {
    	List<PetsStatsDTO> list = salesService.calcPetAgeTypeRatio();
        return list;
    }
    
}