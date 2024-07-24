package com.exam.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.dto.UsersDTO;
import com.exam.service.UsersService;

@RestController
public class UsersController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UsersService usersService;
	
	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@PostMapping("/auth/signup")
	public ResponseEntity<UsersDTO> createUsers(@Valid @RequestBody UsersDTO usersDTO) {
		logger.info("logger:{}", usersDTO);

		String ecrptPW = new BCryptPasswordEncoder().encode(usersDTO.getPw());
		usersDTO.setPw(ecrptPW);
		
		int num = usersService.saveUsers(usersDTO);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usersDTO.getId())
                .toUri();
			return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/auth/{id}")
	public UsersDTO findById(@PathVariable String id ) {
		
		return new UsersDTO();
	}
	
	
}