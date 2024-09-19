package com.exam.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DiscountProductDTO;
import com.exam.dto.DiscountsDTO;
import com.exam.service.DiscountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DiscountsController {

    DiscountsService discountsService;

    public DiscountsController(DiscountsService discountsService) {
        this.discountsService = discountsService;
    }

    @GetMapping("/home/discounts")
    public ResponseEntity<List<DiscountProductDTO>> findDiscountProduct() {
        try {
            List<DiscountProductDTO> list = discountsService.findDiscountProduct();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findDiscountProduct: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/home/featured")
    public ResponseEntity<List<DiscountProductDTO>> findProductsPetType() {
        try {
            List<DiscountProductDTO> list = discountsService.findProductsPetType();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findProductsPetType: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/home/top-selling-products")
    public ResponseEntity<List<DiscountProductDTO>> sortProductsBySalesPrice() {
        try {
            List<DiscountProductDTO> list = discountsService.sortProductsBySalesPrice();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in sortProductsBySalesPrice: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/home/discounts-test")
    public ResponseEntity<List<DiscountsDTO>> discountProductJoin() {
        try {
            List<DiscountsDTO> list = discountsService.discountProductJoin();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in discountProductJoin: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

}
