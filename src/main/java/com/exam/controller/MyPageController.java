package com.exam.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.exam.dto.UsersDTO.PwRequest;
import com.exam.dto.UsersDTO.UsersModifyDTO;
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
			
			if (modifyDTO.getPw() != null && !modifyDTO.getPw().isEmpty()) {
				logger.info("modifiedUser1:{}", modifyDTO.getPw());
				try {
					String ecrptPW = new BCryptPasswordEncoder().encode(modifyDTO.getPw());
					modifyDTO.setPw(ecrptPW);
//				    modifyDTO.setPw(passwordEncoder.encode(modifyDTO.getPw()));
				} catch (Exception e) {
				    logger.error("Password encoding failed: {}", e);
				}
				logger.info("modifiedUser2");
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
	
//	@PostMapping("/pwCheck")
//	public ResponseEntity<Boolean> PwCheck(@RequestBody UsersDTO.PwRequest pwRequest){
//		try {
//			Users user = usersService.findByUserId(pwRequest.getUserId());
//			if(user != null && 
//					passwordEncoder.matches(pwRequest.getPw(), user.getPw())) {
//				return ResponseEntity.ok(true);
//			} else {
//				return ResponseEntity.badRequest().body(false);
//			} 
//		}	catch (Exception e) {
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//			}
//		
//	}
	
	
}