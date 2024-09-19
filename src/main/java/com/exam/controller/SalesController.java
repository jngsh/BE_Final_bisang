package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class SalesController {

    SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/recent-week")
    public ResponseEntity<List<SalesStatsDTO>> findRecentWeekSales() {
        try {
            List<SalesStatsDTO> list = salesService.findRecentWeekSales();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findRecentWeekSales: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/hourly/{date}")
    public ResponseEntity<List<SalesStatsDTO>> findHourlySalesByDate(@PathVariable LocalDate date) {
        try {
            List<SalesStatsDTO> list = salesService.findHourlySalesByDate(date);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findHourlySalesByDate: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/daily/{year}/{month}")
    public ResponseEntity<List<SalesStatsDTO>> findDailySalesByMonth(@PathVariable int year, @PathVariable int month) {
        try {
            List<SalesStatsDTO> list = salesService.findDailySalesByMonth(year, month);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findDailySalesByMonth: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/monthly/{year}")
    public ResponseEntity<List<SalesStatsDTO>> findMonthlySalesByYear(@PathVariable int year) {
        try {
            List<SalesStatsDTO> list = salesService.findMonthlySalesByYear(year);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findMonthlySalesByYear: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/yearly")
    public ResponseEntity<List<SalesStatsDTO>> findYearlySales() {
        try {
            List<SalesStatsDTO> list = salesService.findYearlySales();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findYearlySales: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> findYears() {
        try {
            List<Integer> list = salesService.findYears();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findYears: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/sort-price")
    public ResponseEntity<List<SalesStatsDTO>> findProductSalesPrice() {
        try {
            List<SalesStatsDTO> list = salesService.findProductSalesPrice();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findProductSalesPrice: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/sort-amount")
    public ResponseEntity<List<SalesStatsDTO>> findProductSalesAmount() {
        try {
            List<SalesStatsDTO> list = salesService.findProductSalesAmount();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findProductSalesAmount: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/products-info")
    public ResponseEntity<List<ProductsDTO>> findProductInfo() {
        try {
            List<ProductsDTO> list = salesService.findProductInfo();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findProductInfo: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/pet-type")
    public ResponseEntity<List<PetsStatsDTO>> calcPetTypeRatio() {
        try {
            List<PetsStatsDTO> list = salesService.calcPetTypeRatio();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in calcPetTypeRatio: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/pet-age-type")
    public ResponseEntity<List<PetsStatsDTO>> calcPetAgeTypeRatio() {
        try {
            List<PetsStatsDTO> list = salesService.calcPetAgeTypeRatio();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in calcPetAgeTypeRatio: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/daily-calendar")
    public ResponseEntity<List<SalesStatsDTO>> findAllDailySales() {
        try {
            List<SalesStatsDTO> list = salesService.findAllDailySales();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findAllDailySales: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}
