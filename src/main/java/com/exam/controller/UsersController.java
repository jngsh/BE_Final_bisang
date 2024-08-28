package com.exam.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.dto.MailDTO;
import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.EmailRequest;
import com.exam.dto.UsersDTO.IdRequest;
import com.exam.service.MailService;
import com.exam.service.UsersService;

@RestController
@RequestMapping("/auth")
public class UsersController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UsersService usersService;
	MailService mailService;
	
	public UsersController(UsersService usersService, MailService mailService) {
		this.usersService = usersService;
		this.mailService = mailService;
	}
	
	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> createUsers(@Valid @RequestBody UsersDTO usersDTO) {
		logger.info("logger:{}", usersDTO);
		
		String ecrptPW = new BCryptPasswordEncoder().encode(usersDTO.getPw());
		usersDTO.setPw(ecrptPW);
		
		int userId = usersService.saveUsers(usersDTO);
		int cartId = usersService.createCartId(userId);
		int deliveryAddressId = usersService.createDeliveryId(userId,usersDTO);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("cartId", cartId);
	    response.put("userId", userId);
	    response.put("deliveryAddressId", deliveryAddressId);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usersDTO.getId())
                .toUri();
			return ResponseEntity.created(location).body(response);
	}
	
	//아이디 중복 확인
	@PostMapping("/idCheck")
	public ResponseEntity<Integer> idCheck(@RequestBody IdRequest idRequest) {
	    String id = idRequest.getId();
	    int exists = usersService.idCheck(id);
	    return ResponseEntity.ok(exists);
	}
	
	//등록된 이메일 확인
	@GetMapping("/checkEmail")
	public boolean checkEmail(@RequestParam String email1, @RequestParam String email2) {
		return usersService.checkEmail(email1, email2);
	}
	
	//임시 비밀번호 생성
	@PostMapping("/findPw")
	public void findPw(@RequestBody EmailRequest request) {
		
		String email1 = request.getEmail1();
		String email2 = request.getEmail2();
		logger.info("email:{},{}",email1, email2);
		
		//임시비밀번호 생성
		String tmpPw = usersService.getTmpPw();
		
		//임시비밀번호 저장
		usersService.findPw(tmpPw, email1, email2);
		
		//메일 생성 & 전송
		MailDTO mail = mailService.createMail(tmpPw, email1, email2);
		mailService.sendMail(mail);
		
		logger.info("mail생성:{}",mail);
		
	}
	
}