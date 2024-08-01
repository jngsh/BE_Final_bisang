package com.exam.security;

import com.exam.dto.UsersDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// token 저장 기능
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class JwtTokenResponse {

	String token;
	int userId;

}
