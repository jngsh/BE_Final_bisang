package com.exam.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.exam.dto.MailDTO;
import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Users;

@Service
@Transactional
public interface MailService {

	public MailDTO createMail(String tmpPw, String email1, String email2);
	public void sendMail(MailDTO mail);
}
