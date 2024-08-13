package com.exam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.Member;
import com.exam.dto.UsersDTO;
import com.exam.security.JwtTokenResponse;
import com.exam.security.JwtTokenService;
import com.exam.security.TokenBlacklistService;
import com.exam.service.AuthenticationService;
import com.exam.service.CartService;
import com.exam.service.UsersService;

@CrossOrigin(origins = "http://10.10.10.143:5173")
@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {
    
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    private  JwtTokenService tokenService;
    @Autowired
    UsersService usersService;
    @Autowired
    CartService cartsService;
//    TokenBlacklistService tokenBlacklistService;
//    private  AuthenticationManager authenticationManager;

    public JwtAuthenticationController(JwtTokenService tokenService) {
        this.tokenService = tokenService;
//        this.authenticationManager= authenticationManager;
    }
    
    
    
    // 로그인 처리 + token 얻기
    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> generateToken(
            @RequestBody Map<String, String> jwtTokenRequest) {
        
    	logger.info("logger: jwtTokenRequest: {}", jwtTokenRequest);
    	
    	UsersDTO usersDTO = usersService.findById(jwtTokenRequest.get("id"));
    	
    	PasswordEncoder passwordEncoder = passwordEncoder(); 
    	UsernamePasswordAuthenticationToken authenticationToken=null;
    	Boolean isCustomer = false;
    	
    	if (usersDTO != null && passwordEncoder.matches(jwtTokenRequest.get("pw"), usersDTO.getPw())) { // 일치하는 사용자와 비번이 일치하면
            List<GrantedAuthority> roles = new ArrayList<>();
            if (usersDTO.getIsCustomer() != null && usersDTO.getIsCustomer() == true) {
                roles.add(new SimpleGrantedAuthority("ROLE_USER")); // 사용자 권한 부여
                isCustomer = true;
            } else {
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한 부여
                isCustomer = false;
            }
            authenticationToken =
            		new UsernamePasswordAuthenticationToken(new UsersDTO (jwtTokenRequest.get("id"), jwtTokenRequest.get("pw")), null, roles); 
           
        }
              
        String token = tokenService.generateToken(authenticationToken);
        Integer cartId = cartsService.getCartIdByUserId(usersDTO.getUserId());
        
        logger.info("logger:userId:{}", usersDTO.getUserId());
        return ResponseEntity.ok(new JwtTokenResponse(token, usersDTO.getUserId(), cartId, isCustomer));
    }
    
    // 암호화 객체 생성
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    //로그인 유지상태 확인
    @GetMapping("/check-login")
    public ResponseEntity<?> checkLogin(@RequestHeader("Authorization") String token){
    	if(token != null && token.startsWith("Bearer ")) {
    		token = token.substring(7);
    		boolean isValid = tokenService.validateToken(token);
    		if (isValid) {
                Authentication auth = tokenService.getAuthentication(token);
                return ResponseEntity.ok(auth);
            }
    	}
    	return ResponseEntity.status(401).body("Unauthorized");
    }
   
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
//        String token = request.get("token");
//
//        if (token == null) {
//            return ResponseEntity.badRequest().body("Token is required");
//        }
//
//        try {
//            tokenBlacklistService.addTokenToBlacklist(token);
//
//            return ResponseEntity.ok().body("Logout successful");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
    
    
    
  
}