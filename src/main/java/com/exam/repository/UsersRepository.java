package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
