package com.exam.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.UsersDTO;
import com.exam.entity.Users;
import com.exam.repository.UsersRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	UsersRepository usersRepository;
	
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	@Override
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

	
}
