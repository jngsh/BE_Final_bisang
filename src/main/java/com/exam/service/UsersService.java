package com.exam.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Users;

@Service
@Transactional
public interface UsersService {

	//public UsersDTO saveUsers(UsersDTO dto);
	//public UsersDTO login(String id, String pw);
	
	public int idCheck(String id);
	public UsersDTO authenticate(Map<String, String> map);
	public int saveUsers(UsersDTO dto);
	public UsersDTO findById(String id);
	public Users findByUserId(Integer userId);
	public Users modifyUser(Integer userId, UsersDTO.UsersModifyDTO modifyDTO);
}
