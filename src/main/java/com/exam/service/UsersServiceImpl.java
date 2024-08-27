package com.exam.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.config.UsersMapper;
import com.exam.dto.CartsDTO;
import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Carts;
import com.exam.entity.DeliveryAddress;
import com.exam.entity.Users;
import com.exam.repository.CartsRepository;
import com.exam.repository.DeliveryAddressRepository;
import com.exam.repository.UsersRepository;

import ch.qos.logback.core.encoder.Encoder;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	UsersMapper usersMapper;
	UsersRepository usersRepository;
	CartsRepository cartsRepository;
	DeliveryAddressRepository deliveryAddressRepository;
	
//	BCryptPasswordEncoder passwordEncoder;
	
	public UsersServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper, CartsRepository cartsRepository, DeliveryAddressRepository deliveryAddressRepository) {
		this.usersRepository = usersRepository;
		this.usersMapper = usersMapper;
		this.cartsRepository = cartsRepository;
		this.deliveryAddressRepository = deliveryAddressRepository;
	}
		
	@Override
	public int idCheck(String id) {
		int result =  usersMapper.idCheck(id);
		return result;
	}
	
	@Override
	public int saveUsers(UsersDTO dto) {
		usersMapper.saveUsers(dto);
		int userId = dto.getUserId();
		logger.info("save{}",userId);
		return userId;
	}
	
	@Override
	public UsersDTO authenticate(Map<String, String> map) {
		return usersMapper.authenticate(map);
	}
	
	@Override
	public UsersDTO findById(String id) {
		return usersMapper.findById(id);
	}
	
	
	@Override
	public Users findByUserId(Integer userId) {
		return usersRepository.findByUserId(userId);
	}
	
	@Override
	public Users modifyUser(Integer userId, UsersDTO.UsersModifyDTO modifyDTO) {
		Users user = usersRepository.findByUserId(userId);
		
		 if (user == null) {
		        logger.error("User with id {} not found", userId);
		        throw new RuntimeException("User not found");
		    }
		 
			if(modifyDTO.getPw() != null) user.setPw(modifyDTO.getPw());
			if(modifyDTO.getPost() != null) user.setPost(modifyDTO.getPost());
			if(modifyDTO.getAddress1() != null) user.setAddress1(modifyDTO.getAddress1());
			if(modifyDTO.getAddress2() != null) user.setAddress2(modifyDTO.getAddress2());
			if(modifyDTO.getEmail1() != null) user.setEmail1(modifyDTO.getEmail1());
			if(modifyDTO.getEmail2() != null) user.setEmail2(modifyDTO.getEmail2());
			if(modifyDTO.getPhone1() != null) user.setPhone1(modifyDTO.getPhone1());
			if(modifyDTO.getPhone2() != null) user.setPhone2(modifyDTO.getPhone2());
			if(modifyDTO.getPhone3() != null) user.setPhone3(modifyDTO.getPhone3());
			
			return usersRepository.save(user);
		
	}
	
	@Override
	public int selectAll(String id) {
		int userid = usersMapper.selectAll(id);
		logger.info("logger select:{}",userid);
		return userid;
	}
	
	@Override
	public int createCartId(Integer userId) {
		Users users = usersRepository.findByUserId(userId);
		
		logger.info("userId?{}",userId);
		Carts cart = new Carts();
		cart.setUsers(users);
		
		Carts createdCart = cartsRepository.save(cart);

		logger.info("cartId?{}",createdCart.getCartId());
		
		return createdCart.getCartId();
	}
	
	@Override
	public int createDeliveryId(Integer userId, UsersDTO usersDTO) {
		Users users = usersRepository.findByUserId(userId);
		
		logger.info("Delivery userId:{}",userId);
		DeliveryAddress deliveryAddress = new DeliveryAddress();
		deliveryAddress.setUsers(users);
		deliveryAddress.setDeliveryName(usersDTO.getUsername());
		deliveryAddress.setAddress1(usersDTO.getAddress1());
		deliveryAddress.setAddress2(usersDTO.getAddress2());
		deliveryAddress.setPost(usersDTO.getPost());
		deliveryAddress.setPhone1(usersDTO.getPhone1());
		deliveryAddress.setPhone2(usersDTO.getPhone2());
		deliveryAddress.setPhone3(usersDTO.getPhone3());
		
		DeliveryAddress createDeliveryAddress = deliveryAddressRepository.save(deliveryAddress);
		
		logger.info("createAddr:{}",createDeliveryAddress.getDeliveryAddressId());
		
		return createDeliveryAddress.getDeliveryAddressId();
	}
	
	@Override
	public boolean checkPassword(Integer userId, String pw) {
		logger.info("UserID:{}", userId);
		Users user = usersRepository.findByUserId(userId);
		if (user == null) {
			logger.error("User with id {} not found", userId);
			return false;
		}
		
		logger.info("User found: {}", user);
		
		String storedPassword = user.getPw();
		
		if (storedPassword == null) {
	        logger.error("Stored password for userId {} is null", userId);
	        return false;
	    }
		logger.info("Stored password: {}", storedPassword);
	    
		logger.info("Input password: {}", pw);
		logger.info("Stored password (encoded): {}", storedPassword);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    boolean matches = passwordEncoder.matches(pw, storedPassword);
	    
	    logger.info("Password match result: {}", matches);
	    
	    return matches;
		
//		return passwordEncoder.matches(pw, user.getPw());
	}
	
//	@Override
//	public int getCartIdByUserId(Integer userId) {
//		Carts cart = cartsRepository.finByUserId(userId);
//		return cart != null? cart.getCartId() : null;
//	}
	
	@Override
	public boolean checkEmail(String email1, String email2) {
		return usersRepository.existsByEmail1AndEmail2(email1, email2);
	}
	
	@Override
	public String getTmpPw() {

		char[] charSet = new char[] {'0','1','2','3','4','5','6','7','8','9',
									'A','B','C','D','E','F','G','H','I','J','K',
									'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
									};
		String pw = "";
		
		int idx = 0;
		for(int i = 0; i <= 8; i++) {
			idx = (int)(charSet.length * Math.random());
			pw += charSet[idx];
		}
		
		logger.info("임시비밀번호:{}",pw);
		
		
		return pw;
	}
	
	@Override
	public void findPw(String tmpPw, String email1, String email2) {

		String ecrptPW = new BCryptPasswordEncoder().encode(tmpPw);
		
		Users users = usersRepository.findByEmail1AndEmail2(email1, email2);
		if(users == null) {
			throw new IllegalArgumentException("사용자가 없습니다.");
		}
		
		users.updatePw(ecrptPW);
		logger.info("임시비번완료:{}",getTmpPw());
	}
	
	@Override
	public String findIdByUserId(int userId) {
		return usersMapper.findIdByUserId(userId);
	}
	
}
