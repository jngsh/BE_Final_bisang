package com.exam.service;

import org.springframework.stereotype.Service;

import com.exam.dto.UsersDTO;

public interface UsersService {

	public UsersDTO saveUsers(UsersDTO dto);
	public UsersDTO login(String id, String pw);
}
