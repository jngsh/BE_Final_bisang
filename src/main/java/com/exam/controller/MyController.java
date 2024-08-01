package com.exam.controller;

import com.exam.entity.Carts;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import com.exam.entity.CartItems;
import com.exam.service.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SessionAttributes("x")
public class MyController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/aaa")
	public String aaa(Model m) {
		m.addAttribute("x", "hello");
		return "HelloWorld";
	}

	@GetMapping("/bbb")
	public String bbb(Model m) {
		String tid = (String)m.getAttribute("x");
		logger.info(">>>>>>>>>>>:{}", tid);
		return "HelloWorld";
	}
}