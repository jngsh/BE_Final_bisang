package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.ItemCategoryDTO;
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
    public List<ItemCategoryDTO> findItemCategory() {
    	List<ItemCategoryDTO> list = categorieService.findItemCategory();
    	return list;
    }
    
}