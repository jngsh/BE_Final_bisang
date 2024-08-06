package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Carts;
import com.exam.entity.Users;
import java.util.List;


public interface CartsRepository extends JpaRepository<Carts, Integer> {
	Carts findByUsersUserId(Integer userId);
	
}
