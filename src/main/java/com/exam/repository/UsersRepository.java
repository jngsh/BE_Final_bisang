package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Users;
import java.util.List;


public interface UsersRepository extends JpaRepository<Users, Integer> {

	Users findByUserId(Integer userId);
//	Users modifyUser(Integer userId, UsersModifyDTO modifyDTO);
	boolean existsByEmail1AndEmail2(String email1, String email2);
	Users findByEmail1AndEmail2(String email1, String email2);
	
}
