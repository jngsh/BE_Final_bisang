package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.BlacklistedToken;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, String> {

}
