package com.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> findItemCategory() {
    	List<Map<String, Object>> list = categorieService.findItemCategory();
    	return list;
    }
    
    @GetMapping("/category/type/{petType}")
    public List<Map<String, Object>> findCategoryByPetType(@PathVariable("petType") String petType) {
        return categorieService.findCategoryByPetType(petType);
    }
    
    @GetMapping("/category/products-list/{petType}/{typeValue}")
    public List<DiscountsDTO> findProductsByCategory(@PathVariable("petType") String petType, @PathVariable("typeValue") String typeValue) {

        Map<String, Object> type = new HashMap<>();
        type.put("petType", petType);
        type.put("typeValue", typeValue);

        List<DiscountsDTO> list = categorieService.findProductsByCategory(type);
        return list;
    }
    
}