package com.exam.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.config.UsersMapper;
import com.exam.dto.CartsDTO;
import com.exam.dto.MailDTO;
import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Carts;
import com.exam.entity.Users;
import com.exam.repository.CartsRepository;
import com.exam.repository.UsersRepository;

import ch.qos.logback.core.encoder.Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JavaMailSender mailSender;

	
	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	String title = "PeterPet 임시 비밀번호 안내 이메일입니다.";
	String message = "안녕하세요. PeterPet 임시 비밀번호 안내입니다."
					+ "\n" +"회원님의 임시비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."
					+ "\n";
	String fromAddress = "peter.pet.bisang@gmail.com";
	
	@Override
	public MailDTO createMail(String tmpPw, String email1, String email2) {

		String email = email1 + "@" + email2;
		MailDTO mail = MailDTO.builder()
						.toAddresss(email)
						.title(title)
						.message(message + tmpPw)
						.fromAddress(fromAddress)
						.build();
		logger.info("mail:{}", mail);
		return mail;
	}
	
	@Override
	public void sendMail(MailDTO mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(mail.getToAddresss());
		mailMessage.setSubject(mail.getTitle());
		mailMessage.setText(mail.getMessage());
		mailMessage.setFrom(mail.getFromAddress());
		mailMessage.setReplyTo(mail.getFromAddress());
		
		logger.info("mail주소:{},{}",mail.getToAddresss(), mail.getFromAddress());
		try {
		    mailSender.send(mailMessage); //실제메일전송
		} catch (MailException e) {
		    logger.error("메일 전송 오류", e);
		    // 추가적인 오류 처리
		}

		
		logger.info("메일전송완료:{}",mailMessage);
	}
}
