package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Qr;

public interface QrRepository extends JpaRepository<Qr, Long> {

	/*
	findAll()
	findById()
	count()
	delete()
	deleteById() 자동 제공 메서드들
	이외의 변수로 조회하기 위해서는 추가로 메서드를 정의해야된다 (Query method 규칙을 따라야됨)
	*/
	
	// save는 자동 제공 메서드라 안 써도 되는 건가??
}
