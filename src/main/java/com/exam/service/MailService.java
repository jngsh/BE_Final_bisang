package com.exam.service;


import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.exam.dto.MailDTO;

@Service
@Transactional
public interface MailService {

	public MailDTO createMail(String tmpPw, String email1, String email2);
	public void sendMail(MailDTO mail);
}
