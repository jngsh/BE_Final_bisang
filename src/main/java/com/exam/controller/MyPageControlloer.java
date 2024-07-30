package com.exam.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.IdRequest;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Users;
import com.exam.service.UsersService;

@RestController
@RequestMapping("/mypage")
public class MyPageControlloer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UsersService usersService;
	
	public MyPageControlloer(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Users> findByUserId(@PathVariable Integer userId){

		logger.info("loggerid:{}", userId);
		
		Users user = usersService.findByUserId(userId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			logger.info("loggerE:{}", user);
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{userId}/profile")
	public ResponseEntity<Users> modifyUser(@PathVariable Integer userId, @RequestBody UsersDTO.UsersModifyDTO modifyDTO){
		try {
			Users modifiedUser = usersService.modifyUser(userId, modifyDTO);
			return ResponseEntity.ok(modifiedUser);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
}