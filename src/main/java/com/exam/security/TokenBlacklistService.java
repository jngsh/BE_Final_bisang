package com.exam.security;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.entity.BlacklistedToken;
import com.exam.repository.BlacklistedTokenRepository;

@Service
public class TokenBlacklistService {

	@Autowired
	BlacklistedTokenRepository blackRepository;
	

    public void addTokenToBlacklist(String token) {
    	BlacklistedToken blacklistedToken = new BlacklistedToken();
    	blacklistedToken.setToken(token);
        blacklistedToken.setBlacklistTime(LocalDateTime.now());
        blackRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackRepository.existsById(token);
    }
}
