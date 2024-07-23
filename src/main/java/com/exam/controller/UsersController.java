package com.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.UsersDTO;
import com.exam.service.UsersService;

@RestController
public class UsersController {

	UsersService usersService;
	
	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@PostMapping("/auth/signup")
	public UsersDTO saveUsers(@RequestBody UsersDTO dto) {
		return usersService.saveUsers(dto);
	}
	
	//@GetMapping("/login/")
	
}