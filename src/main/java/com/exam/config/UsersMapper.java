package com.exam.config;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.UsersDTO;


@Mapper
public interface UsersMapper {

	public int idCheck(String id);
	public UsersDTO authenticate(Map<String, String> map);
	public int saveUsers(UsersDTO dto);
	public UsersDTO findById(String id);
	public String findIdByUserId(int userId);
	
}
