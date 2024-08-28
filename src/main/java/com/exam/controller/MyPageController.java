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

import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.PwRequest;
import com.exam.entity.Users;
import com.exam.service.UsersService;

@RestController
@RequestMapping("/mypage")
public class MyPageController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UsersService usersService;
	
	
	
	public MyPageController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	//마이페이지 조회
	@GetMapping("/{userId}")
	public ResponseEntity<Users> findByUserId(@PathVariable Integer userId){

		Users user = usersService.findByUserId(userId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//마이페이지 수정
	@PutMapping("/{userId}/profile")
	public ResponseEntity<Users> modifyUser(@PathVariable Integer userId, @RequestBody UsersDTO.UsersModifyDTO modifyDTO){
		
		try {
			
			if (modifyDTO.getPw() != null && !modifyDTO.getPw().isEmpty()) {
				try {
					String ecrptPW = new BCryptPasswordEncoder().encode(modifyDTO.getPw());
					modifyDTO.setPw(ecrptPW);
				} catch (Exception e) {
				    logger.error("Password encoding failed: {}", e);
				}
			}
			
			Users modifiedUser = usersService.modifyUser(userId, modifyDTO);
			if (modifiedUser != null) {
	            return ResponseEntity.ok(modifiedUser);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	//비밀번호 
	@PostMapping("/pwCheck")
	public boolean checkPassword(@RequestBody PwRequest request){
		return usersService.checkPassword(request.getUserId(), request.getPw());
	}
	
	
}