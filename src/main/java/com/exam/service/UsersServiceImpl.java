package com.exam.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.config.UsersMapper;
import com.exam.dto.UsersDTO;
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
	
	
	
	/*@Override
	public UsersDTO saveUsers(UsersDTO dto) {
		ModelMapper mapper = new ModelMapper();
		Users users = mapper.map(dto, Users.class);
		
		usersRepository.save(users);
		return dto;
	}
	
	@Override
	public UsersDTO login(String id, String pw) {
		ModelMapper mapper = new ModelMapper();
		Users user = usersRepository.findById(Integer.parseInt(id)).orElse(null);
        if (user != null && user.getPw().equals(pw)) {
            return mapper.map(user, UsersDTO.class);
        }
        return null;
	}
	*/
	
	
}
