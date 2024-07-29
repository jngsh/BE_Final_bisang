package com.exam.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.QrDTO;
import com.exam.entity.Qr;
import com.exam.repository.QrRepository;

@Service
public class QrServiceImpl implements QrService {
	
	Logger logger = LoggerFactory.getLogger(QrServiceImpl.class);

	private QrRepository qrRepository;

	// 생성자로 의존성 주입
	public QrServiceImpl(QrRepository qrRepository) {
		this.qrRepository = qrRepository;
	}

	@Override
	public void saveQrData(QrDTO qrDTO) {
		ModelMapper mapper = new ModelMapper(); // 변환 쉽게 해주는 것
		
		// QrDTO --> Qr
		Qr qr = mapper.map(qrDTO, Qr.class);
		qrRepository.save(qr);
		
//		Qr qr = new Qr(); // ?
//		qr.setData(qrDTO.getData());
		logger.info("QR.setdata: {}", qr.getData());
//		
//		qrRepository.save(qr); // 여기서 기본 제공 메서드 쓰는 구나,,, 흠
//		
		logger.info("Saved QR data: {}", qr.getData());
	}
	
	
	
	
	
}
