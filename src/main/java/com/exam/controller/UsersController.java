package com.exam.controller;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.IdRequest;
import com.exam.service.UsersService;

@CrossOrigin(origins = "http://10.10.10.143:5173")
@RestController
@RequestMapping("/auth")
public class UsersController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UsersService usersService;
	
	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> createUsers(@Valid @RequestBody UsersDTO usersDTO) {
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
	
	@PostMapping("/idCheck")
	public ResponseEntity<Integer> idCheck(@RequestBody IdRequest idRequest) {
	    String id = idRequest.getId();
	    int exists = usersService.idCheck(id);
	    return ResponseEntity.ok(exists);
	}

	
	@GetMapping("/{id}")
	public UsersDTO findById(@PathVariable String id ) {
		
		return new UsersDTO();
	}
	
	
}