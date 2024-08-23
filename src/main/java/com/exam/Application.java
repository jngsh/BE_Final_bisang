package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		// AWS 빌드할 때 아래 코드 세 줄 주석 처리 해주기 (env 파일을 못 찾는 이슈가 있음)
//		Dotenv dotenv = Dotenv.load();
//		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
//		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
		
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	
	

}
