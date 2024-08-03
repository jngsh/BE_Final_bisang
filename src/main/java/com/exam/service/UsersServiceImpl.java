package com.exam.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.config.UsersMapper;
import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Users;
import com.exam.repository.UsersRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	Logger logger = LoggerFactory.getLogger(getClass());
	UsersMapper usersMapper;
	
	UsersRepository usersRepository;
	
	public UsersServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper) {
		this.usersRepository = usersRepository;
		this.usersMapper = usersMapper;
	}
		
	@Override
	public int idCheck(String id) {
		int result =  usersMapper.idCheck(id);
		return result;
	}
	
	@Override
	public int saveUsers(UsersDTO dto) {
		return usersMapper.saveUsers(dto);
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
}
