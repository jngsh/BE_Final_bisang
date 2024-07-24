package com.exam.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exam.dto.UsersDTO;

public interface UsersService {

	//public UsersDTO saveUsers(UsersDTO dto);
	//public UsersDTO login(String id, String pw);
	
	public UsersDTO idCheck(String id);
	public UsersDTO authenticate(Map<String, String> map);
	public int saveUsers(UsersDTO dto);
	public UsersDTO findById(String id);
}
