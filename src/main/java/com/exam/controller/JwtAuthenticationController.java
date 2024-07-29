package com.exam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.Member;
import com.exam.dto.UsersDTO;
import com.exam.security.JwtTokenResponse;
import com.exam.security.JwtTokenService;
import com.exam.security.TokenBlacklistService;
import com.exam.service.AuthenticationService;
import com.exam.service.UsersService;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {
    
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    private  JwtTokenService tokenService;
    
//    private  AuthenticationManager authenticationManager;

    public JwtAuthenticationController(JwtTokenService tokenService) {
        this.tokenService = tokenService;
//        this.authenticationManager= authenticationManager;
    }

    /*
      작업1: authenticate 요청해서 먼저 인증처리하고 token을 받는다.
            반드시 POST로 요청하고 permitAll() 지정한다.
            
      request 요청:
      {
        "userid":"inky4832",
        "password":"1234",
      }
      
      response :
       {
         token: "TOKEN_VALUE"
       }
       
     
     */
    
    
    @Autowired
    UsersService usersService;
    TokenBlacklistService tokenBlacklistService;
    
    // 로그인 처리 + token 얻기
    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> generateToken(
            @RequestBody Map<String, String> jwtTokenRequest) {
        
    	logger.info("logger: jwtTokenRequest: {}", jwtTokenRequest);
    	
    	UsersDTO usersDTO = usersService.findById(jwtTokenRequest.get("id"));
    	
    	PasswordEncoder passwordEncoder = passwordEncoder(); 
    	UsernamePasswordAuthenticationToken authenticationToken=null; 
    	
    	if (usersDTO != null && passwordEncoder.matches(jwtTokenRequest.get("pw"), usersDTO.getPw())) { // 일치하는 사용자와 비번이 일치하면
            List<GrantedAuthority> roles = new ArrayList<>();
            if (usersDTO.getIs_customer() != null && usersDTO.getIs_customer()) {
                roles.add(new SimpleGrantedAuthority("ROLE_USER")); // 사용자 권한 부여
            } else {
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한 부여
            }
            authenticationToken =
            		new UsernamePasswordAuthenticationToken(new UsersDTO (jwtTokenRequest.get("id"), jwtTokenRequest.get("pw")), null, roles); 
           
        }
              
        String token = tokenService.generateToken(authenticationToken);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }
 // 암호화 객체 생성
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
    
    
    
    @GetMapping("/authenticate-hello")
    public String authenticateHello() {
    	return "authenticateHello";
    }
}