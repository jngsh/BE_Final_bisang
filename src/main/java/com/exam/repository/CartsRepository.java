package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Carts;


public interface CartsRepository extends JpaRepository<Carts, Integer> {
	Carts findByUsersUserId(Integer userId);
	
}
