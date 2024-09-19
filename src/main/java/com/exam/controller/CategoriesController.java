package com.exam.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DiscountsDTO;
import com.exam.service.CategoriesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CategoriesController {

    CategoriesService categorieService;

    public CategoriesController(CategoriesService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/home/item-category")
    public ResponseEntity<List<Map<String, Object>>> findItemCategory() {
        try {
            List<Map<String, Object>> list = categorieService.findItemCategory();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findItemCategory: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/category/type/{petType}")
    public ResponseEntity<List<Map<String, Object>>> findCategoryByPetType(@PathVariable String petType) {
        try {
            List<Map<String, Object>> list = categorieService.findCategoryByPetType(petType);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findCategoryByPetType: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/category/products-list")
    public ResponseEntity<List<DiscountsDTO>> findAllCategoryProducts() {
        try {
            List<DiscountsDTO> list = categorieService.findAllCategoryProducts();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Error in findAllCategoryProducts: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}
